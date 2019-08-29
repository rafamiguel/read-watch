<?PHP
$hostname_localhost="localhost";
$database_localhost="readandwatch";
$username_localhost="root";
$password_localhost="";

$json=array();
 if(isset($_GET["txtNombre"]) && isset($_GET["txtApellido"]) && isset($_GET["txtCorreo"]) && isset($_GET["txtContrasena"]) && isset($_GET["txtTelefono"]) && isset($_GET["txtDescripcion"])){
  $txtNombre=$_GET['txtNombre'];
  $txtApellido=$_GET['txtApellido'];
  $txtCorreo=$_GET['txtCorreo'];
  $txtContrasena=$_GET['txtContrasena'];
  $txtTelefono=$_GET['txtTelefono'];
  $txtDescripcion=$_GET['txtDescripcion'] ;


  $conexion = new mysqli($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

  $consulta="SELECT idUsuario from usuario WHERE correo = '{$txtCorreo}'";  
  $resultado=mysqli_query($conexion, $consulta);

 if(is_null(mysqli_fetch_array($resultado))){
   $json['usuario'][]=array("existencia" => "no",);
  echo json_encode($json);
  $insert="INSERT INTO usuario(correo, contrasena, nombre, apellidos, telefono, descripcion, rutaFoto, tipo, estado) VALUES ('{$txtCorreo}', '{$txtContrasena}', '{$txtNombre}', '{$txtApellido}', '{$txtTelefono}', '{$txtDescripcion}', 'hshsh', 'E', 'N')";  
 
  if($conexion->query($insert)===TRUE){
   
   
   $resultado = $conexion->query("SELECT * FROM usuario WHERE correo = '{$txtCorreo}'");
   //$resultado=mysqli_query($conexion, $consulta);
  
   if($registro=mysqli_fetch_array($resultado)){
    $json['usuario'][]=$registro;
   }
   mysqli_close($conexion);
   echo json_encode($json);
   
  }else{
   $resulta["txtCorreo"]="No registra";
   $resulta["txtContrasena"]="NO registra";
   $resulta["txtNombre"]="NO registra";
   $resulta["txtApellido"]="No registra";
   $resulta["txtTelefono"]="NO registra";
   $resulta["txtDescripcion"]="NO registra";
   $json['usuario'][]=$resulta;
   echo json_encode($json);
  }
  }else{
    $json['usuario'][]=array("existencia" => "si",);
    echo json_encode($json);
  }

 }else{
   $resulta["txtCorreo"]="WS no retorna";
   $resulta["txtContrasena"]="WS no retorna";
   $resulta["txtNombre"]="WS no retorna";
   $resulta["txtApellido"]="WS no retorna";
   $resulta["txtTelefono"]="WS no retorna";
   $resulta["txtDescripcion"]="WS no retorna";
  $json['usuario'][]=$resulta;
  echo json_encode($json);
 }
?>