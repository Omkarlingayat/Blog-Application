import React, { useEffect, useState } from 'react'
import { ListGroup, ListGroupItem } from 'reactstrap'
import { loadAllCategories } from '../services/CategorieService'
import { toast } from 'react-toastify'
import { Link } from 'react-router-dom'

const CategorieSideMenu = () => {
  
    const [categories, setCategories] = useState([])
    
    useEffect(() => {
        loadAllCategories().then(data => {
            console.log("Categories = "+data)
            setCategories([...data])
        })
        .catch(error => {
            console.log(error);
            toast.error("Error in loading categories")
        })
    },[])

    return (
    <div>
        
        <ListGroup>
            <ListGroupItem action={true} tag={Link} to="/">
                All Bogs
            </ListGroupItem>

            {
                categories && categories.map((cat, index) => {
                    return(
                        <ListGroupItem tag={Link} to={'/categories/'+cat.categorieId} key={index} action={true} className='border pt-1'>
                            {cat.categorieTitle}
                        </ListGroupItem>
                    )
                })
            }

        </ListGroup>

    </div>
  )
}

export default CategorieSideMenu