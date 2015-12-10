/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infrastructure;

import java.util.List;
import models.ReservationModel;

/**
 *
 * @author AlexanderSteen
 */
public interface IAdminService {
    public List<ReservationModel> getReservations();
}
