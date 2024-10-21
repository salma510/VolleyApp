<?php  
include_once '../racine.php'; 
include_once RACINE.'/service/EtudiantService.php'; 

extract($_POST); // Récupérer les données envoyées en POST

$es = new EtudiantService(); 

// Mettre à jour l'étudiant
$es->update(new Etudiant($id, $nom, $prenom, $ville, $sexe)); 

// Redirection après mise à jour
header("location:../index.php"); 
?>

