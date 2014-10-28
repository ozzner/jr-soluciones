<?php 
//ini_set("SMTP","aspmx.l.google.com");
$nombre = $_POST["nombres"]; 
$mail = $_POST["correo"]; 

$header = 'From: ' . $mail . " rn"; 
$header .= "X-Mailer: PHP/" . phpversion() . " rn"; 
$header .= "Mime-Version: 1.0 rn"; 
$header .= "Content-Type: text/plain"; 
echo 'Aqui estoy';

$mensaje = "Este mensaje fue enviado por " . $nombre . " rn"; 
$mensaje .= "Su e-mail es: " . $mail . " rn"; 
$mensaje .= "Mensaje: " . $_POST['mensaje'] . " rn"; 
$mensaje .= "Enviado el " . date('d/m/Y', time()); 

$para = 'rsantillanc@gmail.com'; 
$asunto = 'Contacto desde la web'; 

mail($para, $asunto, utf8_decode($mensaje), $header); 

echo 'estatus=OK'; 
?> 
