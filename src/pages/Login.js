import { Button, Card, CardBody, CardHeader, Col, Container, Form, FormGroup, Input, Label, Row } from "reactstrap";
import Base from "../components/Base";
import { useState } from "react";
import { toast } from "react-toastify";
import { loginUser } from "../services/UserService";
import { doLogin } from "../auth/Index";
import { Navigate, useNavigate } from "react-router-dom";


const Login = () => {
  const navigate  = useNavigate()
  const [loginDetails, setLoginDetails] = useState({
    email:'',
    password:''
  })

  const handleChange = (event, field)=>{
    let actualValue=event.target.value
    setLoginDetails({
      ...loginDetails,
      [field]:actualValue
    })
  }

  const handleFormOnSubmit=(event)=>{
    event.preventDefault();
    console.log(loginDetails);
    // validations
    if(loginDetails.email.trim()==='' || loginDetails.password.trim()===''){
      toast.error("Username and Password both are required!!");
      return;
    }

    // submit the data to server to generate token
    loginUser(loginDetails).then((data)=>{
      console.log("User login")
      console.log(data);
      //toast.success("login success")
      // save the data to localstorage
      doLogin(data,()=>{
        console.log("Login details are saved to local storage")
        // redirect to user dashboard page
        navigate("/user/dashboard")
      })
      toast.success("Login success")
    }).catch(error=>{
      console.log(error);
      if(error.response?.status==400 || error.response?.status==404){
        toast.error(error.response.data.message);  
      }
      else{
        toast.error("Something went wrong on server side!!");
      }
      
    })
  }

  const handleReset = () => {
    setLoginDetails({
      email:'',
      password:''
    });
  }

  return (
    <Base>
      <Container>
        <Row className="mt-4">
          <Col sm={{size:6, offset:3}}>
            <Card color="dark" inverse>
              
              <Container className="text-center"> 
                <CardHeader>
                    <h3>Login Here!!</h3>
                </CardHeader>
              </Container>
              
              <CardBody>
                  <Form 
                  onSubmit={handleFormOnSubmit}
                  >

                    {/* Email */}
                    <FormGroup>
                      <Label for="email">Enter Email</Label>
                      <Input 
                        type="email" 
                        id="email1" 
                        placeholder="Enter here" 
                        value={loginDetails.email}
                        onChange={(e)=>handleChange(e,'email')}
                        ></Input>
                    </FormGroup>
                    
                    {/* Password */}
                    <FormGroup>
                      <Label for="password">Enter Password</Label>
                      <Input 
                        type="password" 
                        id="password" 
                        placeholder="Enter here" 
                        value={loginDetails.password}
                        onChange={(e)=>handleChange(e,'password')}
                        ></Input>
                    </FormGroup>

                    <Container className="text-center">
                        <Button color="success">Login</Button>
                        <Button type="reset" style={{marginLeft:"10px"}} onClick={handleReset}
                        >Reset</Button>
                    </Container>
                  </Form>
              </CardBody>
            </Card>
          </Col>
        </Row>
      </Container>
    </Base>
  );
};

export default Login;