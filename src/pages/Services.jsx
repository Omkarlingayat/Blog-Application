import Base from "../components/Base";
import userContext from "../context/userContext";

const Services = () =>{
    return(
        <userContext.Consumer>
            {
                (user)=>(
                    <Base>
                        <h1>This is services page</h1>
                        <h6>welcome {user.name}</h6>
                    </Base>
                )
            }
        </userContext.Consumer>
    );
}

export default Services;