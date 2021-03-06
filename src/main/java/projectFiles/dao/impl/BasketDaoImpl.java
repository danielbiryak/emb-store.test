package projectFiles.dao.impl;

import projectFiles.utils.ConnectionPool;
import projectFiles.dao.BasketDao;
import projectFiles.dao.exception.DaoException;
import projectFiles.entity.Basket;
import projectFiles.entity.Product;
import org.springframework.stereotype.Repository;
import projectFiles.utils.ConnectionSingleton;

import java.sql.*;

public class BasketDaoImpl implements BasketDao {
    private static final String BASKET_FIELD = "userid, productid, basketstate";
    private static final String BASKET_FIELD_FULL = "id ,userid, productid, basketstate";
    private static final String SELECT_BY_ID = "select basketstate, products.id as productId, products.creatorid as creatorid ,products.prodname, products.price, products.prodquantity, products.prodinfo, basket.id as basketId" +
            " from basket inner join products on products.id = basket.productid where basket.userid = ?";
    private static final String INSERT_SQL = "insert into basket(" + BASKET_FIELD + ") values(?,?,?)";
    private static final String DELETE_SQL = "delete from basket where id = ?";
    private static final String CLEAR_BASKET_SQL = "delete from basket where userid = ?";
    private static final String UPDATE_SQL = "update basket set basketstate = ? where userid = ?";

    private ConnectionPool connectionPool = ConnectionSingleton.getConnection();

    private ProductDaoImpl productDaoImpl = new ProductDaoImpl();

    @Override
    public void insertBasket(Basket basket) throws DaoException {
        try (Connection connection = connectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)
        ) {
            preparedStatement.setString(3, basket.getBasketState().toString());
            preparedStatement.setInt(1, basket.getBuyerId());

            for (Product product : basket.getBasketList()) {
                preparedStatement.setInt(2, product.getId());
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    @Override
    public void clearBasket(Basket basket) throws DaoException {
        try (
                Connection connection = connectionPool.get();
                PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_BASKET_SQL)
        ) {
            preparedStatement.setInt(1, basket.getBuyerId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    @Override
    public void deletePositionOfBasket(Integer id) throws DaoException {
        try (
                Connection connection = connectionPool.get();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    @Override
    public void updateBasket(Basket basket) throws DaoException {
        try (
                Connection connection = connectionPool.get();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)
        ) {
            preparedStatement.setString(1, basket.getBasketState().toString());
            preparedStatement.setInt(2, basket.getBuyerId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    @Override
    public Basket getUsersBasket(Integer userId) throws DaoException {
        Basket basket = new Basket();
        try (
                Connection connection = connectionPool.get();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)
        ) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            basket.setBuyerId(userId);
            while (resultSet.next()) {
                basket.setBasketState(resultSet.getString("basketstate"));
                Product product = new Product(
                        resultSet.getInt("productid"),
                        resultSet.getString("prodname"),
                        resultSet.getInt("price"),
                        resultSet.getInt("prodquantity"),
                        resultSet.getString("prodinfo"),
                        resultSet.getInt("creatorid")
                );
                basket.addToIdList(resultSet.getInt("basketid"));
                basket.addToBasketList(product);
            }

        } catch (SQLException e) {
            throw new DaoException();
        }
        return basket;
    }

    @Override
    public void buyPositionOfBasket(Integer productId, Connection connectionTemp) throws DaoException {
        try (Connection connection = connectionTemp;
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, productId);
            preparedStatement.execute();
        } catch (SQLException throwable) {
            throw new DaoException();
        }
    }
}