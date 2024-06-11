import React, { useEffect, useState } from 'react'
import Base from '../components/Base'
import { useParams } from 'react-router-dom'
import { Col, Container, Row } from 'reactstrap'
import CategorieSideMenu from '../components/CategorieSideMenu'
import { loadAllCategories } from '../services/CategorieService'
import { toast } from 'react-toastify'
import Post from '../components/Post'

const Categories = () => {
  
 const [post, setPost] = useState([])
  const {categorieId} = useParams()

  useEffect(()=>{
    console.log(categorieId);
    loadAllCategories(categorieId).then(data => {
        setPost([...data])
    })
    .catch(error => {
        console.log(error)
        toast.error("Error in loading posts")
    })
  },[])

    return (
    <Base>
        <Container className='mt-3'>

            <Row>
                <Col md={3} className="pt-5">
                    <CategorieSideMenu/>
                </Col>

                <Col md={9}>
                    {
                        post && post.map((post, index) =>{
                            return(
                                <Post key={index} post={post}/>
                            )
                        })
                    }
                </Col>

            </Row>

        </Container>
    </Base>
  )
}

export default Categories