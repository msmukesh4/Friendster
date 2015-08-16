<?php

	// $comment = "comment";
	// $image = "image";
	$comment = $_POST['comment'];	
	$image = $_POST['image'];

	$server = 'mysql.hostinger.in';
	$user = 'u681508416_coder';
	$root = 'u681508416_image';
	$password = 'webserver123';

	// Estrablishing Connection With DataBase

	$mysqli = new mysqli($server,$user,$password,$root);
	if($mysqli->maxdb_connect_errno){
		//Print when any error occers...
		echo " Failed to connect to MySQL : (" . $mysqli->maxdb_connect_errno . ")" . $mysqli->connect_error;
	}
	
	$sql = "INSERT INTO Comment(NAME,COMMENT) VALUES ('$image','$comment')";
	if ($mysqli->query($sql))
	{
		echo "Insertion Successful";
	}else{
		echo "Insertion Unsuccessful";
	}

?>