import React from 'react'
import { Link } from 'react-router-dom'
import {Card, CardBody, CardText } from 'reactstrap'

const Post = ({post={ title:"This is default post title", content:"This is default post content."}}) => {
  console.log(post);
  return (
    
    <Card className='mb-2'>
      <CardBody className='border-0 shadow-sm' style={{backgroundColor:"#aad6df"}}>
        <h1>{post.title}</h1>
        
        <CardText dangerouslySetInnerHTML={{__html:post.content.substring(0,60) + "..."}}>

        </CardText>

        <div>
          <Link className='btn btn-secondary border-0' to={"/post/"+post.id}>Read More</Link>
        </div>
      </CardBody>
    </Card>
  )
}

export default Post