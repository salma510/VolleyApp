<?php
// Inclusion des classes nécessaires
include_once __DIR__ . '/../classes/Etudiant.php';
include_once __DIR__ . '/../connexion/Connexion.php';
include_once __DIR__ . '/../dao/IDao.php';

class EtudiantService implements IDao {
    // Propriété pour la connexion à la base de données
    private $connexion;

    // Constructeur pour initialiser la connexion
    public function __construct() {
        $this->connexion = new Connexion();
    }

    // Méthode pour créer un nouvel étudiant
    public function create($o) {
        try {
            $query = "INSERT INTO etudiant (nom, prenom, ville, sexe) VALUES (:nom, :prenom, :ville, :sexe)";
            $req = $this->connexion->getConnexion()->prepare($query);

            // Récupération des valeurs de l'objet $o
            $nom = $o->getNom();
            $prenom = $o->getPrenom();
            $ville = $o->getVille();
            $sexe = $o->getSexe();

            // Liaison des paramètres
            $req->bindParam(':nom', $nom);
            $req->bindParam(':prenom', $prenom);
            $req->bindParam(':ville', $ville);
            $req->bindParam(':sexe', $sexe);

            // Exécution de la requête et gestion du résultat
            if ($req->execute()) {
                echo json_encode(["status" => "success", "message" => "Étudiant créé avec succès"]);
            } else {
                $errorInfo = $req->errorInfo();
                echo json_encode(["status" => "error", "message" => "Erreur lors de la création de l'étudiant", "error" => $errorInfo]);
            }
        } catch (PDOException $e) {
            echo json_encode(["status" => "error", "message" => "Erreur SQL : " . $e->getMessage()]);
        }
    }

    // Méthode pour supprimer un étudiant
    public function delete($o) {
        $query = "DELETE FROM Etudiant WHERE id = :id";
        $req = $this->connexion->getConnexion()->prepare($query);
        
        // Bind de la valeur
        $req->bindValue(':id', $o->getId());
        
        try {
            $req->execute();
        } catch (Exception $e) {
            die('Erreur SQL lors de la suppression : ' . $e->getMessage());
        }
    }

    // Méthode pour récupérer la liste de tous les étudiants
    public function findAll() {
        $etds = array();
        $query = "SELECT * FROM Etudiant";
        $req = $this->connexion->getConnexion()->prepare($query);
        
        try {
            $req->execute();
            while ($e = $req->fetch(PDO::FETCH_OBJ)) {
                $etds[] = new Etudiant($e->id, $e->nom, $e->prenom, $e->ville, $e->sexe);
            }
        } catch (Exception $e) {
            die('Erreur SQL lors de la récupération des étudiants : ' . $e->getMessage());
        }
        return $etds;
    }

    // Méthode pour récupérer un étudiant par son ID
    public function findById($id) {
        $query = "SELECT * FROM Etudiant WHERE id = :id";
        $req = $this->connexion->getConnexion()->prepare($query);
        
        // Bind de la valeur
        $req->bindValue(':id', $id);
        
        try {
            $req->execute();
            if ($e = $req->fetch(PDO::FETCH_OBJ)) {
                return new Etudiant($e->id, $e->nom, $e->prenom, $e->ville, $e->sexe);
            }
        } catch (Exception $e) {
            die('Erreur SQL lors de la récupération de l\'étudiant : ' . $e->getMessage());
        }
        return null; // Renvoie null si aucun étudiant n'est trouvé
    }

    // Méthode pour mettre à jour un étudiant
    public function update($o) {
        $query = "UPDATE Etudiant SET nom = :nom, prenom = :prenom, ville = :ville, sexe = :sexe WHERE id = :id";
        $req = $this->connexion->getConnexion()->prepare($query);
        
        // Bind des valeurs
        $req->bindValue(':nom', $o->getNom());
        $req->bindValue(':prenom', $o->getPrenom());
        $req->bindValue(':ville', $o->getVille());
        $req->bindValue(':sexe', $o->getSexe());
        $req->bindValue(':id', $o->getId());
        
        try {
            $req->execute();
        } catch (Exception $e) {
            die('Erreur SQL lors de la mise à jour : ' . $e->getMessage());
        }
    }

    // Méthode pour récupérer tous les étudiants pour une API
    public function findAllApi() {
        $query = "SELECT * FROM Etudiant";
        $req = $this->connexion->getConnexion()->prepare($query);
        
        try {
            $req->execute();
            return $req->fetchAll(PDO::FETCH_ASSOC);
        } catch (Exception $e) {
            die('Erreur SQL lors de la récupération des étudiants pour l\'API : ' . $e->getMessage());
        }
    }
}
?>
