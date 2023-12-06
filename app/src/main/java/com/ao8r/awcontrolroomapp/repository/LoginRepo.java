package com.ao8r.awcontrolroomapp.repository;

import android.content.Context;
import android.content.Intent;


import com.ao8r.awcontrolroomapp.controller.ConnectionHelper;
import com.ao8r.awcontrolroomapp.customiz_widgets.CustomToast;
import com.ao8r.awcontrolroomapp.utils.ControlRoomConstants;
import com.ao8r.awcontrolroomapp.views.MenuScreen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginRepo {

    static Connection connection;

    public static void login(final Context context, final String loginPassword) {

        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");

            } else {

                String query = "SELECT * FROM Mob_Users WHERE Name= ? AND Pass = ?";
//                    Statement statement = connection.createStatement();
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, ControlRoomConstants.LOGIN_USER);
                statement.setString(2, loginPassword);


                ResultSet resultSet = statement.executeQuery();


                if (resultSet.next()) {
                    CustomToast.customToast(context, "تم تسجيل الدخول بنجاح");
                    ControlRoomConstants.CURRENT_USER_ID = resultSet.getInt("ID");
                    ControlRoomConstants.USER_CONTROL = resultSet.getInt("Flag");

//            used with security levels

//                    if(StoresConstants.USER_CONTROL != 3){
//                        StoresConstants.STORE_NUMBER = "0";
//                    }else{
//                        StoresConstants.STORE_NUMBER = resultSet.getString("Sector");
//                    }

//
                    Intent intent = new Intent(context, MenuScreen.class);
                    context.startActivity(intent);

                } else {
                    CustomToast.customToast(context, "بيانات خاطئة");

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}


