<?php
//$conexion = mysqli_connect("localhost", "root", "", "basepw");
$conexion = mysqli_connect("remotemysql.com", "mqcJIDsAs0", "H1N2GDzVd1", "mqcJIDsAs0");
if (mysqli_connect_errno()) {
    printf("No se pudo establecer la conexion %s\n", mysqli_connect_error());
    exit();
}
//$resultado=mysqli_select_db($conexion, "base");
$resultado=mysqli_select_db($conexion, "mqcJIDsAs0");
mysqli_set_charset($conexion, "utf8");
?>