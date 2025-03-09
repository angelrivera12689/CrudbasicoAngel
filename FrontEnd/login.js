  // Esta función se ejecuta al enviar el formulario
  function validateForm() {
    const username = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    // Validación del nombre de usuario y la contraseña
    if (username === "Angel" && password === "12345") {
        // Si son correctos, se redirige a la página de destino
        window.location.href = "/CrudbasicoAngel/FrontEnd/pages/Funcionalidad.html"; // Cambia esta URL a donde desees redirigir
        return false; // Prevenir el envío del formulario tradicional
    } else {
        // Si los datos son incorrectos, mostrar un mensaje de error
        alert("Nombre de usuario o contraseña incorrectos. Inténtalo nuevamente.");
        return false; // Prevenir el envío del formulario tradicional
    }
}