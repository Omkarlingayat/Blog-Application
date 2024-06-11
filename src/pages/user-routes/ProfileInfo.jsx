import React, { useContext } from "react";
import Base from "../../components/Base";
import userContext from "../../context/userContext";

const ProfileInfo = () => {
    const user = useContext(userContext)

    return(
        <Base>
            <p>User profile</p>
            <h5>welcome {user.name}</h5>
        </Base>
    )
}

export default ProfileInfo;