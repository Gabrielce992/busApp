// src/App.jsx
import React from "react";
import "./styles/globals.css";
import BusesTable from "./components/tables/BusesTable";

export default function App() {
  return (
    <div>
      <header style={{ marginBottom: 12 }}>
        <h1>Gestión de Buses</h1>
        <p style={{ margin: 0, color: "#6b7280" }}>Panel administrativo — Listado y detalles</p>
      </header>

      <main>
        <BusesTable />
      </main>
    </div>
  );
}
