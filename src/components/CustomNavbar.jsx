import "bootstrap/dist/css/bootstrap.min.css";
import React, { useEffect, useState } from "react";
// It will not refresh page
import { NavLink as ReactLink, useNavigate } from "react-router-dom";
import {
  Collapse,
  Navbar,
  NavbarToggler,
  NavbarBrand,
  Nav,
  NavItem,
  NavLink,
  UncontrolledDropdown,
  DropdownToggle,
  DropdownMenu,
  DropdownItem,
} from "reactstrap";
import { doLogOut, getCurrentUserDetails, isLoggedIn } from "../auth/Index";

function Example() {
  let navigate = useNavigate();
  const [isOpen, setIsOpen] = useState(false);
  const [login, setLogin] = useState(false);
  const [user, setUser] = useState(undefined);

  useEffect(() => {
    setLogin(isLoggedIn());
    setUser(getCurrentUserDetails());
  }, [login])

  const logout = () => {
    doLogOut(()=>{
      // logged out
      setLogin(false)
      navigate("/")
    })
  }

  return (
    <div>
      <Navbar color="dark" dark expand="md" fixed="">
        <NavbarBrand tag={ReactLink} to="/">
          My Blogs
        </NavbarBrand>
        <NavbarToggler onClick={() => setIsOpen(!isOpen)} />
        <Collapse isOpen={isOpen} navbar>
          <Nav className="me-auto" navbar>
            <NavItem>
              <NavLink tag={ReactLink} to="/home">
                Home
              </NavLink>
            </NavItem>
            <NavItem>
              <NavLink tag={ReactLink} to="/about">
                About
              </NavLink>
            </NavItem>
            <NavItem>
              <NavLink tag={ReactLink} to="/services">
                Services
              </NavLink>
            </NavItem>

            <UncontrolledDropdown nav inNavbar>
              <DropdownToggle nav caret>
                More
              </DropdownToggle>
              <DropdownMenu>
                <DropdownItem>You Tube</DropdownItem>
                <DropdownItem divider />
                <DropdownItem>Instagram</DropdownItem>
                <DropdownItem divider />
                <DropdownItem>LinkedIn</DropdownItem>
              </DropdownMenu>
            </UncontrolledDropdown>
          </Nav>

          <Nav navbar>
            {login && (
              <>
              console.log(login);

                <NavItem>
                    <NavLink tag={ReactLink} to="/user/profile-info">
                      Profile Info
                    </NavLink>
                </NavItem>
                
                <NavItem>
                  <NavLink onClick={logout}>Logout</NavLink>
                </NavItem>

                <NavItem>
                  <NavLink tag={ReactLink} to="/user/dashboard">
                    {user.email}
                  </NavLink>
                </NavItem>
              </>
            )}
            {!login && (
              <>
              console.log(login)
                <NavItem>
                  <NavLink tag={ReactLink} to="/login">
                    Login
                  </NavLink>
                </NavItem>
                <NavItem>
                  <NavLink tag={ReactLink} to="/signup">
                    Signup
                  </NavLink>
                </NavItem>
              </>
            )}
          </Nav>
        </Collapse>
      </Navbar>
    </div>
  );
}

export default Example;
