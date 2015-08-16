<?php

	$name = $_POST['img_name'];
	
	$server = 'mysql.hostinger.in';
	$user = 'u681508416_coder';
	$root = 'u681508416_image';
	$password = 'webserver123';

	$mysqli = new mysqli($server,$user,$password,$root);
	if($mysqli->maxdb_connect_errno){
		//Print when any error occers...
		echo " Failed to connect to MySQL : (" . $mysqli->maxdb_connect_errno . ")" . $mysqli->connect_error;
	}

	//Querying and storing it in $res variable
	$nameList = $mysqli->query("SELECT COMMENT FROM Comment WHERE NAME='$name'");

	//Starting $res From Start...
	$nameList->data_seek(0);
	$allComments = "";


	while ($row = $nameList->fetch_assoc()) {
		$allComments .="".$row['COMMENT']."//"; 		
	}

	echo "".$allComments;


?>