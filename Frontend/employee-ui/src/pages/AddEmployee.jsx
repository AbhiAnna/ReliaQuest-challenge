import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/client";

export default function AddEmployee(){
  const [form, setForm] = useState({
    firstName:"", lastName:"", email:"",
    jobTitle:"", salary:"", age:""
  });
  const [err, setErr] = useState("");
  const [ok, setOk] = useState("");
  const [submitting, setSubmitting] = useState(false);
  const navigate = useNavigate();

  const update = (k,v)=> setForm(f=>({...f,[k]:v}));

  async function onSubmit(e){
    e.preventDefault();
    setSubmitting(true); setErr(""); setOk("");

    const body = {
      firstName: form.firstName || null,
      lastName: form.lastName || null,
      email: form.email || null,
      jobTitle: form.jobTitle || undefined,
      salary: form.salary === "" ? undefined : Number(form.salary),
      age: form.age === "" ? undefined : Number(form.age),
    };

    try{
      const { data } = await api.post("/employee", body);
      setOk("Employee created");
      navigate(`/employees/${data.uuid}`);
    }catch(e){
      setErr(e?.response?.data?.message || e.message);
    }finally{
      setSubmitting(false);
    }
  }

  return (
    <div className="card">
      <h1 className="h1">Add Employee</h1>
      {err && <div className="alert error">{err}</div>}
      {ok && <div className="alert success">{ok}</div>}

      <form className="form" onSubmit={onSubmit}>
        <div className="row">
          <div>
            <div className="label">First name</div>
            <input className="input" value={form.firstName} onChange={e=>update("firstName", e.target.value)} />
          </div>
          <div>
            <div className="label">Last name</div>
            <input className="input" value={form.lastName} onChange={e=>update("lastName", e.target.value)} />
          </div>
        </div>

        <div>
          <div className="label">Email</div>
          <input className="input" value={form.email} onChange={e=>update("email", e.target.value)} />
          <div className="help"></div>
        </div>

        <div className="row">
          <div>
            <div className="label">Job title</div>
            <input className="input" value={form.jobTitle} onChange={e=>update("jobTitle", e.target.value)} />
          </div>
          <div>
            <div className="label">Salary</div>
            <input className="input" inputMode="numeric" value={form.salary} onChange={e=>update("salary", e.target.value)} />
          </div>
        </div>

        <div className="row">
          <div>
            <div className="label">Age</div>
            <input className="input" inputMode="numeric" value={form.age} onChange={e=>update("age", e.target.value)} />
          </div>
          <div className="help" style={{alignSelf:"end"}}></div>
        </div>

        <div className="actions">
          <button className="btn" disabled={submitting} type="submit">
            {submitting ? "Creating…" : "Create employee"}
          </button>
        </div>
      </form>
    </div>
  );
}

