import { Link, useParams } from "react-router-dom";
import Base from "../components/Base";
import { Container, Row, Col, CardBody, CardText, Card, Input, Button } from "reactstrap";
import { useEffect, useState } from "react";
import { createComment, loadPost } from "../services/PostService";
import { toast } from "react-toastify";
import { BASE_URL } from "../services/Helper";
import { getCurrentUserDetails } from "../auth/Index";

const PostPage = () => {

  const { postId } = useParams();
  const [post, setPost] = useState(null);
  const [comment, setComment] = useState({
    content:''
  });

  useEffect(() => {
    // load post for post id
    loadPost(postId)
      .then((data) => {
        console.log(data);
        setPost(data);
      })
      .catch((error) => {
        console.log(error);
        toast.error("Error in loading post");
      });
  }, []);

  const printDate = (numbers) => {
    return new Date(numbers).toLocaleDateString();
  };

  // call url from post service
  const submitComment = () => {

      if(comment.content.trim()===''){
        return
      }

      const user = getCurrentUserDetails();
      if(!user){
        toast.error("You have to login to post a comment");
        return;
      }

      createComment(user.id ,postId, comment)
      .then(data=>{
        //console.log("---------------- data = "+data)
        toast.success("Comment added ...")
        setPost({
          ...post,
          comment:[...post.comment, data.data]
        })
        setComment({
          content:''
        })
      }).catch(error=>{
        console.log("-------------- error = "+error)
      })
  }

  return (
    <Base>
      <Container>
        <Link to="/">Home</Link> / {post && (<Link to="">{post.title}</Link>)}

        <Row>
          <Col
            md={{
              size: 12,
            }}
          >
            <Card className="mt-3">
              {post && (
                <CardBody style={{backgroundColor:"#93828438"}}>
                  <CardText>
                    Posted By <b>{post.user.name} </b>
                    on <b>{printDate(post.addedDate)}</b>
                  </CardText>

                   <CardText>
                        <span className="text-muted">{post.categorie.categorieTitle}</span>
                   </CardText>

                    <div className="divider" style={{
                        width:'100%',
                        height:'1px',
                        background:'#e2e2e2'
                    }}>

                    </div>

                  <CardText>
                    {post.title}
                  </CardText>

                  <div
                    className="image-container mt-3 container text-center shadow"
                    style={{ width: "50%", Height: "50%" }}
                  >
                    
                      <img
                        className="img-fluid"
                        src={`${BASE_URL}/api/post/get/image/${post.imageName}`}
                        alt=""
                      />
                    
                  </div>

                  <CardText
                    className="mt-3"
                    dangerouslySetInnerHTML={{ __html: post.content }}
                  ></CardText>

                </CardBody>
              )}
            </Card>
          </Col>
        </Row>

       {/* ----------------------------------------- Comment section ---------------------------*/}
            
        <Row className="mt-3">
          <Col md={
            {
              size:9,
              offset:1
            }
          }>

            <h6>Comments ({post ? post.comments.length : 0})</h6>
            {
              post && post.comments.map((c, index)=>(
                <Card className="mt-1 border-0" key={index}>
                  <CardBody style={{backgroundColor:"#93828438"}}>
                    <CardText>
                      {c.content}
                    </CardText>
                  </CardBody>
                </Card>
              ))
            }

            <Card className="mt-4 border-0">
              <CardBody>
                <CardText className="text-center">

                  <Input type="textarea" 
                  placeholder="Write your comment here" 
                  value={comment.content} 
                  onChange={(event)=> setComment({content:event.target.value})}></Input>
                  
                  <Button color="primary" className="mt-2" onClick={submitComment}>Submit</Button>
                </CardText>
              </CardBody>
            </Card>

          </Col>
        </Row>

      </Container>
    </Base>
  );
};

export default PostPage;
