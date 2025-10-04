// src/components/tables/BusesTable.jsx
import React, { useState, useEffect } from "react";
import { fetchBuses, fetchBusById } from "../../services/busService";
import "../../styles/table.css";

export default function BusesTable() {
  const [buses, setBuses] = useState([]);
  const [page, setPage] = useState(0);
  const [size] = useState(10);
  const [loading, setLoading] = useState(false);
  const [totalPages, setTotalPages] = useState(1);

  useEffect(() => {
    let mounted = true;
    setLoading(true);
    fetchBuses(page, size)
      .then(data => {
        if (!mounted) return;
        setBuses(data.content || []);
        setTotalPages(data.totalPages || 1);
      })
      .catch(err => {
        console.error(err);
        alert("Error cargando buses: " + err.message);
      })
      .finally(() => mounted && setLoading(false));
    return () => { mounted = false; };
  }, [page, size]);

  const handleView = async (id) => {
    try {
      const bus = await fetchBusById(id);
      alert(JSON.stringify(bus, null, 2));
    } catch (err) {
      alert("Error: " + err.message);
    }
  };

  return (
    <section className="table-container" aria-live="polite">
      <div className="table-header">
        <h2>Listado de buses</h2>
        <div className="table-meta">Página {page + 1} de {totalPages}</div>
      </div>

      {loading ? (
        <div className="loader" role="status" aria-label="Cargando">Cargando...</div>
      ) : (
        <>
          <div className="table-wrapper">
            <table className="bus-table">
              <thead>
                <tr>
                  <th>ID</th><th>Número</th><th>Placa</th><th>Marca</th><th>Estado</th><th>Acción</th>
                </tr>
              </thead>
              <tbody>
                {buses.length === 0 ? (
                  <tr><td colSpan="6" className="empty">No hay buses</td></tr>
                ) : buses.map(bus => (
                  <tr key={bus.id}>
                    <td data-label="ID">{bus.id}</td>
                    <td data-label="Número">{bus.numeroBus}</td>
                    <td data-label="Placa">{bus.placa}</td>
                    <td data-label="Marca">{bus.marcaNombre}</td>
                    <td data-label="Estado">{bus.estado}</td>
                    <td data-label="Acción">
                      <button className="view-btn" onClick={() => handleView(bus.id)}>Ver</button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          <div className="pagination">
            <button onClick={() => setPage(p => Math.max(0, p - 1))} disabled={page === 0}>⬅ Anterior</button>
            <span> Página {page + 1} de {totalPages} </span>
            <button onClick={() => setPage(p => Math.min(totalPages - 1, p + 1))} disabled={page >= totalPages - 1}>Siguiente ➡</button>
          </div>
        </>
      )}
    </section>
  );
}
