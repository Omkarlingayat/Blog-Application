import { useEffect, useRef, useState } from "react";
import { Button, Card, CardBody, Container, Form, FormGroup, Input, Label} from "reactstrap";
import { loadAllCategories } from "../services/CategorieService";
import JoditEditor from "jodit-react";
import { createPost as doCreatePost, uploadImage } from "../services/PostService";
import { getCurrentUserDetails } from "../auth/Index";
import { toast } from "react-toastify";

const AddPost = () => {

    const edtior = useRef(null)
    //const [content, setContent] = useState('');
    const [categories, setCategories] = useState([])
    const [user, setUser] = useState(undefined)
    const [image, setImage] = useState(undefined)

    const [post, setPost] = useState({
        title:'',
        content:'',
        categoryId:-1
    })

    const resetData = () => {
        console.log("reseting data");
        setPost(prevState => ({
            ...prevState,
            title: '',
            content: '',
            categoryId: -1
        }));
    };

    useEffect(
        () => {
            setUser(getCurrentUserDetails())
            loadAllCategories().then((data)=>{
                console.log(data)
                setCategories(data)
            }).catch(error=>{
                console.log(error)
            })
        },
        []
    )

    // field changed function
    const fieldChanged = (event) =>{
        //console.log(event.target.name);
        //console.log(event.target.value);
        //console.log(event);
        setPost({...post,[event.target.name]:event.target.value})
    }

    const contentFieldChanged=(data)=>{
        setPost({...post,'content':data})
    }

    // upload image
    const submitFile = (event) => {
        const file = event.target.files[0];

        if(file){
            uploadImage(post.id,file).then((imageData)=>{
                setImage(imageData);
            })
            .catch((error)=>{
                console.log("Error uploading image :- "+error);
                toast.error("Error while uploading image")
            });
        }
    }

    const createPost = (event)=>{
        event.preventDefault();
        console.log("form submitted");
        if(post.title.trim()===''){
            toast.error("Post title is required!!");
            return;
        }
        if(post.content.trim()===''){
            toast.error("Post content is required");
            return;
        }
        if(post.categoryId===-1){
            toast.error("Select Post categoryId");
            return;
        }
    
        
        // submit the form on server
        post['userId'] = user.id;
        doCreatePost(post)
        .then(data => {
            console.log("Post created successfully:", data);
            toast.success("Post created successfully!!");
            // after post creating remove data from page
            resetData();
            //console.log(post);
        })
        .catch(error => {
            console.log("Error creating post:", error);
            toast.error("Error creating post: " + error);
        });
    };
    
    return(
        <div className="wrapper">
            <Card className="mt-4" style={{backgroundColor:"#f1eaea"}}>
                {/* {JSON.stringify(post)} */}
                <CardBody>
                    <h1>What going in your mind ?</h1>
                    <Form onSubmit={createPost}>
                        
                        <FormGroup>
                            <Label for="title">Post Title</Label>
                            <Input type="text" id="title" placeholder="Enter here" onChange={fieldChanged} name="title"></Input>
                        </FormGroup>
                        
                        <FormGroup>
                            <Label for="content">Post Content</Label>
                            {/* <Input type="textarea" id="content" placeholder="Enter here" style={{height:"200px"}}></Input> */}
                            <JoditEditor ref={edtior} value={post.content} onChange={contentFieldChanged} /> 
                        </FormGroup>
                        
                        <FormGroup>
                            <Label for="category">Post Category</Label>
                            <Input type="select" id="category" name="categoryId" onChange={fieldChanged} defaultValue={0}>
                                <option disabled value={0}>-- Select Category --</option>
                                {
                                    categories.map((category) => (
                                        <option value={category.categorieId} key={category.categorieId}>
                                            {category.categorieTitle}
                                        </option>
                                    ))
                                }
                            </Input>
                        </FormGroup>
                        
                        <FormGroup>
                            <Label for="image">Select Image</Label>
                            <Input type="file" id="image" name="imageId" onChange={submitFile}></Input>
                        </FormGroup>
                        
                        <Container className="text-center"> 
                            <Button color="primary" type="submit">Create Post</Button>
                            <Button type="reset" color="danger" style={{marginLeft:"10px"}} onClick={resetData}>Reset Content</Button>
                        </Container>
                    </Form>
                </CardBody>
            </Card>
        </div>
    )   
}

export default AddPost;