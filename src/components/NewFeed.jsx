import React, { useEffect, useState } from 'react'
import { loadAllPosts } from '../services/PostService'
import { Col, Container, Pagination, PaginationItem, PaginationLink, Row } from 'reactstrap'
import Post from './Post'
import { toast } from 'react-toastify'
import InfiniteScroll from 'react-infinite-scroll-component'

const NewFeed = () => {
    
    const [postContent, setPostContent] = useState({
        content:[],
        pageNumber:'',
        pageSize:'',
        totalPages:'',
        totalElements:'',
        lastPage:false
    })

    const [currentPage, setCurrentPage] = useState(0)

    useEffect(()=>{
        // load all posts from the server
        changePage(currentPage)

    },[currentPage])

    const changePage = (pageNumber = 0, pageSize = 5)=>{
        // for next button
        if(pageNumber > postContent.pageNumber && postContent.lastPage){
            return
        }

        // for previous button
        if(pageNumber < postContent.pageNumber && postContent.pageNumber==0){
            return
        }

        loadAllPosts(pageNumber, pageSize).then(data => {
            setPostContent({
                content:[...postContent.content, ...data.content],
                totalPages: data.totalPages,
                totalElements: data.totalElements,
                pageSize: data.pageSize,
                lastPage: data.lastPage,
                pageNumber: data.pageNumber
            })
            console.log(data)
        }).catch(error =>{
            toast.error("Error in loading posts")
        })
    }

    const changePageInfinite = ()=>{
        console.log("page changed")
        setCurrentPage(currentPage+1)
    }

  return (
    <div className="container-fluid">
        <Row>
            <Col md={
                {
                    size:12
                }
            }>

                <h3>Blog Count ({postContent?.totalElements})</h3>
                
                <InfiniteScroll
                    dataLength={postContent.content.length}
                    next={changePageInfinite}
                    hasMore={!postContent.lastPage}
                    loader={<h4>Loading...</h4>}
                    endMessage={
                        <p style={{ textAlign: 'center' }}>
                        <b>Yay! You have seen it all</b>
                        </p>
                    }
                >
                    {
                        postContent.content.map((post)=>(
                            <Post post={post} key={post.id}/>
                        ))
                    }

                </InfiniteScroll>


                {/*<Container className='text-center mt-3'>
                    
                    <Pagination size='lg'>
                    
                        <PaginationItem disabled={postContent.pageNumber==0} onClick={()=>changePage(postContent.pageNumber-1)}>
                            <PaginationLink previous>
                                Previous
                            </PaginationLink>
                        </PaginationItem>

                        {
                            [...Array(postContent.totalPages)].map((item, index)=>(
                            
                                <PaginationItem key={index} active={index==postContent.pageNumber} onClick={() => changePage(index)}>
                                    <PaginationLink>
                                        {index+1}
                                    </PaginationLink>
                                </PaginationItem>
                            ))

                                
                        }

                        <PaginationItem onClick={()=>changePage(postContent.pageNumber+1)} disabled={postContent.lastPage}>
                            <PaginationLink next >
                                Next
                            </PaginationLink>
                        </PaginationItem>
                    
                    </Pagination>
                
                </Container> */}

            </Col>
        </Row>

    </div>
  )
}

export default NewFeed