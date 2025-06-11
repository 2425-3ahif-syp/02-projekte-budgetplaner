public static void main(String[] args) {
    try (Connection conn = getConnection()) {
        System.out.println("Verbindung erfolgreich hergestellt!");
        createTable(conn);       // Tabelle anlegen
        insertFakeData(conn);    // Beispieldaten einfügen
        System.out.println("Tabelle erstellt und Daten eingefügt!");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}