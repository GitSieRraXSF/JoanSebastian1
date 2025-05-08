package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Carro;

public class CarroDAO {
	private Connection connection;

	public CarroDAO(Connection connection) {
		this.connection = connection;
	}

	public ArrayList<Carro> fetchDisponibles() {
		ArrayList<Carro> carros = new ArrayList<>();
		String sql = "SELECT * FROM Carro WHERE is_deleted=0";
		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				int referencia = rs.getInt("referencia");
				int cantidad = rs.getInt("cantidad");
				String marca = rs.getString("marca");
				String modelo = rs.getString("modelo");
				String color = rs.getString("color");
				Carro carro = new Carro(referencia, marca, modelo, color, cantidad);
				carros.add(carro);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return carros;
	}

	public ArrayList<Carro> fetchEliminados() {
		ArrayList<Carro> carros = new ArrayList<>();
		String sql = "SELECT * FROM Carro WHERE is_deleted=1";
		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				int referencia = rs.getInt("referencia");
				int cantidad = rs.getInt("cantidad");
				String marca = rs.getString("marca");
				String modelo = rs.getString("modelo");
				String color = rs.getString("color");
				Carro carro = new Carro(referencia, marca, modelo, color, cantidad);
				carros.add(carro);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return carros;
	}

	public void eliminar(int referencia) {
		String sql = "UPDATE Book SET is_deleted = 1 WHERE referencia = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setLong(1, referencia);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void recuperar(int referencia) {
		String sql = "UPDATE Book SET is_deleted = 0 WHERE referencia = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setLong(1, referencia);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}