import React from "react";
import AddPost from "../../components/AddPost";
import { Container } from "reactstrap";
import Base from "../../components/Base";

const UserDashboard = () => {
    return(
        <Base>
           <Container>
                <AddPost/>
            </Container> 
        </Base>
    )
}

export default UserDashboard;