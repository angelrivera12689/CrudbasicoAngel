async function actualizarAsistente(idAsistente) {
    try {
        // Construir la URL de la solicitud PUT con el parámetro idAsistente
        let url = `http://localhost:8080/api/v1/assistants/${idAsistente}`;

        // Recoger los valores del formulario (puedes adaptar los IDs de los campos según tu formulario)
        let nombre = document.getElementById("asistente-nombre").value;
        let email = document.getElementById("asistente-email").value;
        let phone = document.getElementById("asistente-phone").value;

        // Validar que los campos no estén vacíos
        if (!nombre || !email || !phone) {
            alert("Por favor, complete todos los campos.");
            return;
        }

        // Crear el objeto JSON con los datos del formulario
        let bodyContent = JSON.stringify({
            name: nombre,
            email: email,
            phone: phone
        });

        // Realizar la solicitud PUT a la API
        let response = await fetch(url, {
            method: "PUT",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: bodyContent
        });

        // Verificar si la respuesta fue exitosa
        if (!response.ok) {
            throw new Error("Error al actualizar el asistente");
        }

        // Convertir la respuesta a JSON
        let data = await response.json();

        // Mostrar mensaje de éxito
        alert('¡Éxito! El asistente se actualizó correctamente.');

        // Limpiar el formulario
        document.getElementById("asistente-update-form").reset();

    } catch (error) {
        console.error("Error:", error);
        alert("Hubo un problema al actualizar el asistente.");
    }
}

// Llamar a la función cuando el usuario haga clic en el botón "Actualizar"
document.getElementById("updateAsistenteButton").addEventListener("click", () => {
    let idAsistente = document.getElementById("asistente-id").value.trim(); // Asumiendo que tienes un campo para ingresar el ID
    if (idAsistente) {
        actualizarAsistente(idAsistente); // Llamar la función para actualizar el asistente con el ID proporcionado
    } else {
        alert("Por favor ingrese un ID de asistente para actualizar.");
    }
});
