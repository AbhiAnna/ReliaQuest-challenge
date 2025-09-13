import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api from "../api/client";

export default function Employees(){
  const [rows, setRows] = useState([]);
  const [loading, setLoading] = useState(true);
  const [err, setErr] = useState("");

  async function load(){
    try{
      setLoading(true);
      const { data } = await api.get("/employee");
      setRows(data || []);
      setErr("");
    }catch(e){
      setErr(e?.response?.data?.message || e.message);
    }finally{
      setLoading(false);
    }
  }
  useEffect(()=>{ load(); },[]);

  return (
    <div className="stack">
      <div className="card">
        <div className="actions" style={{justifyContent:"space-between"}}>
          <h1 className="h1">Employees</h1>
          <button className="btn" onClick={load}>Refresh</button>
        </div>

        {loading && <p>Loading…</p>}
        {err && <div className="alert error">{err}</div>}

        {!loading && !err && (
          rows.length === 0 ? <p className="help">No employees yet.</p> :
          <table className="table">
            <thead>
              <tr>
                <th>UUID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Job Title</th>
              </tr>
            </thead>
            <tbody>
              {rows.map(e => (
                <tr key={e.uuid}>
                  <td className="mono">
                    <Link to={`/employees/${e.uuid}`}>{e.uuid}</Link>
                  </td>
                  <td>{[e.firstName, e.lastName].filter(Boolean).join(" ") || "-"}</td>
                  <td>{e.email}</td>
                  <td>{e.jobTitle || "-"}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}
