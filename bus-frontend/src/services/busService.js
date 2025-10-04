// src/services/busService.js
const BACKEND = import.meta.env.VITE_BACKEND_URL || "http://localhost:8080";
// Para dev: user:password -> base64 dXNlcjpwYXNzd29yZA==
const basicAuth = 'Basic ' + btoa('user:password');

export async function fetchBuses(page = 0, size = 10) {
  const res = await fetch(`${BACKEND}/bus?page=${page}&size=${size}`, {
    headers: {
      'Authorization': basicAuth,
      'Content-Type': 'application/json'
    }
  });

  if (!res.ok) {
    const text = await res.text().catch(() => '');
    throw new Error(text || 'Error al obtener buses');
  }
  return res.json();
}

export async function fetchBusById(id) {
  const res = await fetch(`${BACKEND}/bus/${id}`, {
    headers: { 'Authorization': basicAuth }
  });

  if (!res.ok) {
    const text = await res.text().catch(() => '');
    throw new Error(text || 'Bus no encontrado');
  }
  return res.json();
}
