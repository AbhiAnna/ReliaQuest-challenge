import { Routes, Route, useNavigate, Link } from "react-router-dom";
import { useState } from "react";
import Employees from "./pages/Employees.jsx";
import AddEmployee from "./pages/AddEmployee.jsx";
import EmployeeDetail from "./pages/EmployeeDetail.jsx";

export default function App() {
  const [uuidInput, setUuidInput] = useState("");
  const navigate = useNavigate();

  function onSearch(e){
    e.preventDefault();
    const v = uuidInput.trim();
    if (v) {
      navigate(`/employees/${v}`);
      setUuidInput("");
    }
  }

  return (
    <>
      <header className="app-header">
        <nav className="nav">
          <div className="brand">
            <span className="brand-badge" />
            <Link to="/" style={{color:"var(--text-100)", textDecoration:"none"}}>Employee Portal</Link>
          </div>
          <Link to="/">Employees</Link>
          <Link to="/add">Add Employee</Link>

          <form className="search" onSubmit={onSearch}>
            <input
              className="input mono"
              placeholder="Search by UUID"
              value={uuidInput}
              onChange={(e)=>setUuidInput(e.target.value)}
            />
            <button className="btn" type="submit">Open</button>
          </form>
        </nav>
      </header>

      <main className="container">
        <Routes>
          <Route path="/" element={<Employees />} />
          <Route path="/add" element={<AddEmployee />} />
          <Route path="/employees/:uuid" element={<EmployeeDetail />} />
        </Routes>
      </main>
    </>
  );
}
