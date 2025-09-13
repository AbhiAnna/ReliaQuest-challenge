import { useParams, Link } from "react-router-dom";
import { useEffect, useState } from "react";
import api from "../api/client";

export default function EmployeeDetail(){
  const { uuid } = useParams();
  const [emp, setEmp] = useState(null);
  const [err, setErr] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(()=>{
    let ignore=false;
    (async ()=>{
      try{
        setLoading(true);
        const { data } = await api.get(`/employee/${uuid}`);
        if(!ignore){ setEmp(data); setErr(""); }
      }catch(e){
        if(!ignore){ setErr(e?.response?.data?.message || e.message); setEmp(null); }
      }finally{
        if(!ignore) setLoading(false);
      }
    })();
    return ()=>{ ignore=true; };
  },[uuid]);

  return (
    <div className="card">
      <h1 className="h1">Employee Detail</h1>
      {loading && <p>Loading…</p>}
      {err && <div className="alert error">{err}</div>}

      {!loading && !err && emp && (
        <div className="stack">
          <div><b>UUID:</b> <span className="mono">{emp.uuid}</span></div>
          <div><b>Name:</b> {[emp.firstName, emp.lastName].filter(Boolean).join(" ") || "-"}</div>
          <div><b>Email:</b> {emp.email}</div>
          <div><b>Job Title:</b> {emp.jobTitle || "-"}</div>
          <div className="row">
            <div><b>Salary:</b> {emp.salary ?? "-"}</div>
            <div><b>Age:</b> {emp.age ?? "-"}</div>
          </div>
          <div className="row">
            <div><b>Hire Date:</b> {emp.contractHireDate || "-"}</div>
            <div><b>Termination Date:</b> {emp.contractTerminationDate || "-"}</div>
          </div>
          <hr className="hr" />
          <div className="actions">
            <Link to="/" className="btn" style={{textDecoration:"none"}}> Back to list</Link>
          </div>
        </div>
      )}
    </div>
  );
}
