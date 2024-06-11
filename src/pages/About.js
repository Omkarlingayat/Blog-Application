import Base from "../components/Base";
import userContext from "../context/userContext";

const About = () =>{
    return(
       <userContext.Consumer>
            {
                (user) => (
                    <Base>
                        <div>
                            <h1>About Page</h1>
                            <p>Welcome in about page</p>
                            <h5>Hello {user.name}</h5>
                        </div>
                    </Base>
                )
            }
       </userContext.Consumer>
    );
}

export default About