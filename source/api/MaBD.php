<?php
class MaBD {
    static private $host = "localhost";
    static private $base = "meteorolog";
    static private $user = "root";
    static private $pass = "";

    static private $pdo = null;

    static function getInstance() {
        if (self::$pdo == null) {
            $dsn = sprintf("mysql:host=%s;dbname=%s;charset=utf8",self::$host, self::$base);
            try {
                self::$pdo = new PDO($dsn, self::$user, self::$pass, array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));
            }
            catch (PDOException $e) {
                exit("Erreur de connexion au serveur");
            }
        }
        return self::$pdo;
    }
}
?>