import React, { useEffect, useState } from "react";
import userContext from "./userContext";

function UserProvider({children}){

    const [user, setUser] = useState({
        name:'Omkar'
    })

    useEffect(()=>{
        setUser({
            name:"Omkar Lingayat"
        })
    },[])

    return(
        <userContext.Provider value={user}>
            {children}
        </userContext.Provider>
    )
}

export default UserProvider;