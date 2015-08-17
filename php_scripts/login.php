<?php
	$userna = $_POST['username'];
	$pass = $_POST['password'];
	$success = 0;

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
	$UserList = $mysqli->query("SELECT USERNAME,PASSWORD FROM User");

	//Starting $res From Start...
	$UserList->data_seek(0);

	while ($row = $UserList->fetch_assoc()) {
		// if ($row['USERNAME']=="mike" && $row['PASSWORD']=="mike") {
		if ($row['USERNAME']==$userna && $row['PASSWORD']==$pass) {
			# code...
			$success++;
		}		
	}

	echo "".$success;
	$success =0;

?>