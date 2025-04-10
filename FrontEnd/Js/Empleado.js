
import { urlBase } from '/FrontEnd/Js/constante.js'; 


const API_URL_EMPLEADO = `${urlBase}employee/`; 

console.log( API_URL_EMPLEADO); 

// Registrar empleado
document.getElementById("persona-form").addEventListener("submit", async function (event) {
    event.preventDefault();

    let bodyContent = JSON.stringify({
        "firstName": document.getElementById("persona-firstName").value,
        "lastName": document.getElementById("persona-lastName").value,
        "address": document.getElementById("persona-address").value,
        "phoneNumber": document.getElementById("persona-phoneNumber").value
    });

    let response = await fetch(API_URL_EMPLEADO, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: bodyContent
    });

    let data = await response.text();
    console.log(data);
    alert("Persona registrada con éxito");
});

// Registrar evento
document.getElementById("evento-form").addEventListener("submit", async function (event) {
    event.preventDefault();

    let eventName = document.getElementById("evento-nombre").value.trim();
    let description = document.getElementById("evento-descripcion").value.trim();
    let location = document.getElementById("evento-ubicacion").value.trim();
    let categoryId = parseInt(document.getElementById("evento-categoria").value.trim());
    let imageUrl = document.getElementById("evento-imagen").value.trim();

    if (!eventName || !description || !location || !imageUrl || isNaN(categoryId)) {
        alert("Todos los campos son obligatorios, y la categoría debe ser un número válido.");
        return;
    }

    // 👇 No se envían ni date ni time
    let bodyContent = JSON.stringify({
        eventName: eventName,
        description: description,
        location: location,
        categoryId: categoryId,
        imageUrl: imageUrl
    });

    try {
        let response = await fetch("http://localhost:8080/api/v1/events/", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "*/*"
            },
            body: bodyContent
        });

        if (!response.ok) {
            const errorBody = await response.text();
            throw new Error("Error en la solicitud: " + response.status + " - " + errorBody);
        }

        let data = await response.json();
        console.log("✅ Evento registrado:", data);
        alert("✅ Evento registrado con éxito");
        document.getElementById("evento-form").reset();
    } catch (error) {
        console.error("❌ Error al registrar el evento:", error);
        alert("❌ Error al registrar el evento. Revisa consola.");
    }
});

// ======================= ACTUALIZAR EMPLEADO =======================
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("empleado-update-form").addEventListener("submit", async function (event) {
        event.preventDefault();

        let id = document.getElementById("empleado-id-update").value.trim();
        let nombre = document.getElementById("empleado-nombre-update").value.trim();
        let apellido = document.getElementById("empleado-apellido-update").value.trim();
        let direccion = document.getElementById("empleado-direccion-update").value.trim();
        let telefono = document.getElementById("empleado-telefono-update").value.trim();

        if (!id || !nombre || !apellido || !direccion || !telefono) {
            alert("⚠️ Todos los campos son obligatorios.");
            return;
        }

        let bodyContent = JSON.stringify({
            "firstName": nombre,
            "lastName": apellido,
            "address": direccion,
            "phoneNumber": telefono
        });

        let url = `${API_URL_EMPLEADO}${id}`;
        console.log(`📡 Enviando petición a: ${url}`);
        console.log("📦 Enviando datos:", bodyContent);

        try {
            let response = await fetch(url, {
                method: "PUT",
                headers: {
                    "Accept": "application/json",
                    "Content-Type": "application/json"
                },
                body: bodyContent
            });

            let data = await response.json();
            console.log("📢 Respuesta del servidor:", data);

            if (response.ok) {
                alert("✅ Empleado actualizado con éxito.");
                document.getElementById("empleado-update-form").reset();
            } else {
                alert(`❌ Error: ${data.message || "No se pudo actualizar el empleado"}`);
            }

        } catch (error) {
            console.error("❌ Error en la petición:", error);
            alert("🚨 Error de conexión.");
        }
    });
});

// Eliminar empleado
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
        const response = await fetch(`${API_URL_EMPLEADO}${id}`, {
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
