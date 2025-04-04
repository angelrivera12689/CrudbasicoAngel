// eliminar asistente
document.getElementById("asistente-delete-form").addEventListener("submit", async function (event) {
    event.preventDefault(); // Evita el comportamiento por defecto del formulario

    const id = document.getElementById("asistente-id-eliminate").value.trim();
    const mensaje = document.getElementById("mensaje"); // Asegúrate de tener este elemento en el HTML

    if (!id) {
        mensaje.innerText = "⚠️ Por favor ingresa un ID válido.";
        mensaje.style.color = "orange";
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/api/v1/assistants/${id}`, {
            method: "DELETE",
            headers: {
                "Accept": "*/*"
            }
        });

        if (response.ok) {
            mensaje.innerText = `✅ Asistente con ID ${id} eliminado correctamente.`;
            mensaje.style.color = "green";
            document.getElementById("asistente-delete-form").reset();
            alert("✅ Asistente eliminado correctamente.");
        } else {
            const data = await response.json();
            mensaje.innerText = `❌ Error al eliminar: ${data.message || "No se pudo eliminar el asistente"}`;
            mensaje.style.color = "red";
        }
    } catch (error) {
        console.error("❌ Error de conexión:", error);
        mensaje.innerText = "⚠️ Error de conexión al eliminar.";
        mensaje.style.color = "red";
    }
});

//eliminar Categoria
document.getElementById("categoria-delete-form").addEventListener("submit", async function (event) {
    event.preventDefault(); // Prevenir envío del formulario

    const id = document.getElementById("categoria-id").value.trim();
    const mensaje = document.getElementById("mensaje"); // Asegúrate de que este elemento exista en tu HTML

    if (!id) {
        mensaje.innerText = "⚠️ Por favor ingresa un ID válido.";
        mensaje.style.color = "orange";
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/api/v1/category-events/${id}`, {
            method: "DELETE",
            headers: {
                "Accept": "*/*"
            }
        });

        if (response.ok) {
            mensaje.innerText = `✅ Categoría con ID ${id} eliminada correctamente.`;
            mensaje.style.color = "green";
            document.getElementById("categoria-delete-form").reset();
            alert("✅ Categoría eliminada correctamente.");
        } else {
            const data = await response.json();
            mensaje.innerText = `❌ Error al eliminar: ${data.message || "No se pudo eliminar la categoría"}`;
            mensaje.style.color = "red";
        }
    } catch (error) {
        console.error("❌ Error de conexión:", error);
        mensaje.innerText = "⚠️ Error de conexión al eliminar.";
        mensaje.style.color = "red";
    }
});

// eliminar empleado
document.getElementById("empleado-delete-form").addEventListener("submit", async function (event) {
    event.preventDefault(); // Prevenir comportamiento por defecto

    const id = document.getElementById("empleado-id-delete").value.trim();
    const mensaje = document.getElementById("mensaje"); // Asegúrate de que exista en tu HTML

    if (!id) {
        mensaje.innerText = "⚠️ Por favor ingresa un ID válido.";
        mensaje.style.color = "orange";
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/api/v1/employee/${id}`, {
            method: "DELETE",
            headers: {
                "Accept": "*/*"
            }
        });

        if (response.ok) {
            mensaje.innerText = `✅ Empleado con ID ${id} eliminado correctamente.`;
            mensaje.style.color = "green";
            document.getElementById("empleado-delete-form").reset();
            alert("✅ Empleado eliminado correctamente.");
        } else {
            const data = await response.json();
            mensaje.innerText = `❌ Error al eliminar: ${data.message || "No se pudo eliminar el empleado"}`;
            mensaje.style.color = "red";
        }
    } catch (error) {
        console.error("❌ Error de conexión:", error);
        mensaje.innerText = "⚠️ Error de conexión al eliminar.";
        mensaje.style.color = "red";
    }
});

// eliminar evento
document.getElementById("evento-delete-form").addEventListener("submit", async function (event) {
    event.preventDefault(); // Prevenir comportamiento por defecto

    const id = document.getElementById("evento-id-delete").value.trim();
    const mensaje = document.getElementById("mensaje"); // Asegúrate de tenerlo en el HTML

    if (!id) {
        mensaje.innerText = "⚠️ Por favor ingresa un ID válido.";
        mensaje.style.color = "orange";
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/api/v1/events/${id}`, {
            method: "DELETE",
            headers: {
                "Accept": "*/*"
            }
        });

        if (response.ok) {
            mensaje.innerText = `✅ Evento con ID ${id} eliminado correctamente.`;
            mensaje.style.color = "green";
            document.getElementById("evento-delete-form").reset();
            alert("✅ Evento eliminado correctamente.");
        } else {
            const data = await response.json();
            mensaje.innerText = `❌ Error al eliminar: ${data.message || "No se pudo eliminar el evento"}`;
            mensaje.style.color = "red";
        }
    } catch (error) {
        console.error("❌ Error de conexión:", error);
        mensaje.innerText = "⚠️ Error de conexión al eliminar.";
        mensaje.style.color = "red";
    }
});

// eliminar organizador
document.getElementById("organizador-delete-form").addEventListener("submit", async function (event) {
    event.preventDefault(); // Evita recargar la página

    const id = document.getElementById("organizador-id-delete").value.trim();
    const mensaje = document.getElementById("mensaje"); // Asegúrate que exista en el HTML

    if (!id) {
        mensaje.innerText = "⚠️ Por favor ingresa un ID válido.";
        mensaje.style.color = "orange";
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/api/v1/organizer/${id}`, {
            method: "DELETE",
            headers: {
                "Accept": "*/*"
            }
        });

        if (response.ok) {
            mensaje.innerText = `✅ Organizador con ID ${id} eliminado correctamente.`;
            mensaje.style.color = "green";
            document.getElementById("organizador-delete-form").reset();
            alert("✅ Organizador eliminado correctamente.");
        } else {
            const data = await response.json();
            mensaje.innerText = `❌ Error al eliminar: ${data.message || "No se pudo eliminar el organizador"}`;
            mensaje.style.color = "red";
        }
    } catch (error) {
        console.error("❌ Error de conexión:", error);
        mensaje.innerText = "⚠️ Error de conexión al eliminar.";
        mensaje.style.color = "red";
    }
});

// eliminar reseña
document.getElementById("reseña-delete-form").addEventListener("submit", async function (event) {
    event.preventDefault(); // Evita recarga

    const id = document.getElementById("reseña-id-delete").value.trim();
    const mensaje = document.getElementById("mensaje"); // Asegúrate de tener este div en el HTML

    if (!id) {
        mensaje.innerText = "⚠️ Por favor ingresa un ID válido.";
        mensaje.style.color = "orange";
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/api/v1/reviews/${id}`, {
            method: "DELETE",
            headers: {
                "Accept": "*/*"
            }
        });

        if (response.ok) {
            mensaje.innerText = `✅ Reseña con ID ${id} eliminada correctamente.`;
            mensaje.style.color = "green";
            document.getElementById("reseña-delete-form").reset();
            alert("✅ Reseña eliminada correctamente.");
        } else {
            const data = await response.json();
            mensaje.innerText = `❌ Error al eliminar: ${data.message || "No se pudo eliminar la reseña"}`;
            mensaje.style.color = "red";
        }
    } catch (error) {
        console.error("❌ Error de conexión:", error);
        mensaje.innerText = "⚠️ Error de conexión al eliminar.";
        mensaje.style.color = "red";
    }
});

// eliminar ticket
document.getElementById("ticket-delete-form").addEventListener("submit", async function (event) {
    event.preventDefault(); // Previene la recarga

    const id = document.getElementById("ticket-id-delete").value.trim();
    const mensaje = document.getElementById("mensaje"); // Asegurate que exista este elemento

    if (!id) {
        mensaje.innerText = "⚠️ Por favor ingresa un ID válido.";
        mensaje.style.color = "orange";
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/api/v1/tickets/${id}`, {
            method: "DELETE",
            headers: {
                "Accept": "*/*"
            }
        });

        if (response.ok) {
            mensaje.innerText = `✅ Ticket con ID ${id} eliminado correctamente.`;
            mensaje.style.color = "green";
            document.getElementById("ticket-delete-form").reset();
            alert("✅ Ticket eliminado correctamente.");
        } else {
            const data = await response.json();
            mensaje.innerText = `❌ Error al eliminar: ${data.message || "No se pudo eliminar el ticket"}`;
            mensaje.style.color = "red";
        }
    } catch (error) {
        console.error("❌ Error de conexión:", error);
        mensaje.innerText = "⚠️ Error de conexión al eliminar.";
        mensaje.style.color = "red";
    }
});
