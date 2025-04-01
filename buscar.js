
 // Función para cargar un ticket basado en su ID
        async function cargarTicketPorId(idTicket) {
            try {
                // Construir la URL de la solicitud GET con el parámetro idTicket
                let url = `http://localhost:8080/api/v1/tickets/${idTicket}`;

                // Realizamos la solicitud GET a la API
                let response = await fetch(url, {
                    method: "GET",
                    headers: {
                        "Accept": "*/*",
                        "User-Agent": "Thunder Client (https://www.thunderclient.com)",
                        "Content-Type": "application/json"
                    }
                });

                // Verificar si la respuesta fue exitosa
                if (!response.ok) {
                    throw new Error("Error al obtener los datos");
                }

                // Convertir la respuesta a JSON
                let registro = await response.json();

                // Referenciar el cuerpo de la tabla
                let tableBody = document.getElementById("tableBody");

                // Limpiar cualquier dato previo
                tableBody.innerHTML = "";

                // Verificar si el ticket existe
                if (!registro) {
                    tableBody.innerHTML = "<tr><td colspan='8'>No se encontró el ticket</td></tr>";
                    return;
                }

                // Insertar el ticket en la tabla
                let fila = `
                    <tr>
                        <td>${registro.idTicket}</td> <!-- ID del ticket -->
                        <td>${registro.eventId}</td> <!-- ID del evento -->
                        <td>${registro.assistantId}</td> <!-- ID del asistente -->
                        <td>${registro.price}</td> <!-- Precio del ticket -->
                        <td>${registro.seatNumber}</td> <!-- Número de asiento -->
                        <td>${new Date(registro.purchaseDate).toLocaleString()}</td> <!-- Fecha de compra (formato legible) -->
                        <td>${registro.status ? 'Activo' : 'Inactivo'}</td> <!-- Estado -->
                        <td class="actions-column">
                            <button class="btn btn-info btn-sm">Editar</button>
                            <button class="btn btn-danger btn-sm">Eliminar</button>
                        </td>
                    </tr>
                `;
                tableBody.innerHTML += fila;

            } catch (error) {
                console.error("Error:", error);
                document.getElementById("tableBody").innerHTML = "<tr><td colspan='8'>Error al cargar datos</td></tr>";
            }
        }

        // Llamar a la función cuando el usuario hace clic en "Buscar"
        document.getElementById("searchButton").addEventListener("click", () => {
            let idTicketBusqueda = document.getElementById("searchInput").value.trim();
            if (idTicketBusqueda) {
                cargarTicketPorId(idTicketBusqueda); // Buscar ticket por idTicket
            } else {
                alert("Por favor ingrese un ID de ticket para buscar.");
            }
        });

        // Llamar a la función para cargar un mensaje o cargar todos los tickets al inicio si es necesario
        document.addEventListener("DOMContentLoaded", () => {
            // Cargar inicialmente todos los tickets si lo deseas, o dejarlo vacío
            // cargarTodosLosTickets();
        });