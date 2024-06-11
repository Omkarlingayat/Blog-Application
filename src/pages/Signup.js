import { Button, Card, CardBody, CardHeader, Col, Container, Form, FormGroup, Input, Label, Row } from "reactstrap";
import Base from "../components/Base";
import "bootstrap/dist/css/bootstrap.min.css";
import { useEffect, useState } from "react";
import { signUp } from "../services/UserService";
import { toast } from "react-toastify";

const Signup = () =>{

    const [data, setData] = useState({
        name:'',
        email:'',
        password:'',
        about:''
    })

    const [error, setError] = useState({
        errors:{},
        isError:false
    })

    // print data on console
    /*useEffect(()=>{
        console.log(data);
    },[data])*/

    // handle change dynamically handle the change
    const handleChange=(event,property)=>{
        //console.log("name changed");
        //console.log(event.target.value);
        setData({...data,[property]:event.target.value})
        
    }

    // reset button function
    const resetData = ()=>{
        setData({
            name:'',
            email:'',
            password:'',
            about:''
        })
    }

    // submit form function
    const submitForm = (event)=>{
        event.preventDefault()
        console.log(data);
        // data validate

        // call server api for sending data
        signUp(data).then((resp)=>{
            console.log(resp);
            console.log("Success log");
            toast.success("User Registered Successfully!!");
            setData({
                name:"",
                email:"",
                password:"",
                about:""
            })
        }).catch((error)=>{
            console.log(error)
            console.log("Error log")
        });
    };

    return(
        <Base>
            <Container>
                <Row className="mt-4">

                    {/*print data */}
                    {/*
                        {JSON.stringify(data)}
                    */}

                    <Col sm={{size:6, offset:3}}>
                        {/* card color is dark but text color is white that is inverse */}
                        <Card color="dark" inverse>
                            
                            <Container className="text-center">
                                <CardHeader>
                                    <h3>Fill Information to Register!!</h3>
                                </CardHeader>
                            </Container>
                            
                            <CardBody>
                                {/* creating form */}
                                <Form onSubmit={submitForm}>
                                    {/* Name */}
                                    <FormGroup>
                                        <Label for="name">Enter Name</Label>
                                        <Input type="text" id="name" placeholder="Enter your full name" onChange={(e)=>handleChange(e,'name')} value={data.name}/>
                                    </FormGroup>

                                    {/* Email */}
                                    <FormGroup>
                                        <Label for="email">Enter Email</Label>
                                        <Input type="email" id="email" placeholder="Enter here" onChange={(e)=>handleChange(e,'email')} value={data.email}></Input>
                                    </FormGroup>

                                    {/* Password */}
                                    <FormGroup>
                                        <Label for="password">Password</Label>
                                        <Input type="password" id="password" placeholder="Enter strong password" onChange={(e)=>handleChange(e,'password')} value={data.password}></Input>
                                    </FormGroup>

                                    {/* About */}
                                    <FormGroup>
                                        <Label for="about">Describe yourself</Label>
                                        <Input  type="textarea" id="about" placeholder="Write something about you" style={{height:"100px"}} onChange={(e)=>handleChange(e,'about')} value={data.about}></Input>
                                    </FormGroup>

                                    {/* register and reset buttons */}
                                    <Container className="text-center">
                                        <Button color="success">Register</Button>
                                        <Button type="reset" style={{marginLeft:"10px"}} onClick={resetData}>Reset</Button>
                                    </Container>
                                </Form>
                            </CardBody>
                        
                        </Card>
                    </Col>
                </Row>
            </Container>
        </Base>
    );
}

export default Signup