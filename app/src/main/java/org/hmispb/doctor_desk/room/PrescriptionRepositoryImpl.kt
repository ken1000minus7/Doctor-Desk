package org.hmispb.doctor_desk.room

import androidx.lifecycle.LiveData
import com.google.gson.Gson
import org.hmispb.doctor_desk.model.LoginRequest
import org.hmispb.doctor_desk.model.LoginResponse
import org.hmispb.doctor_desk.model.Prescription
import org.hmispb.doctor_desk.model.SavePrescriptionRequest

class PrescriptionRepositoryImpl(private val prescriptionDao: PrescriptionDao, private val prescriptionApi: PrescriptionApi) :
    PrescriptionRepository {

    override suspend fun insertPrescription(prescription: Prescription) {
        prescriptionDao.insertPrescription(prescription)
    }

    override fun getAllPrescriptions(): LiveData<List<Prescription>> {
        return prescriptionDao.getAllPrescriptions()
    }

    override suspend fun deletePrescription(prescription: Prescription) {
        prescriptionDao.deletePrescription(prescription)
    }

    override suspend fun deleteAllPrescriptions() {
        prescriptionDao.deleteAllPrescriptions()
    }

    override suspend fun savePrescription(
        prescription: Prescription,
        hospitalCode: String,
        userId: String
    ) {
        val prescriptionString = Gson().toJson(prescription)
        val request = SavePrescriptionRequest(hospitalCode,userId,prescriptionString)
        prescriptionApi.savePrescription(request)
    }

    override suspend fun login(username: String, password: String): LoginResponse? {
        return prescriptionApi.login(LoginRequest(listOf(username,password)))
    }

    override suspend fun setUploaded(id: Int) {
        prescriptionDao.setUploaded(id)
    }

    override suspend fun containsNotUploaded(): Boolean {
        return prescriptionDao.notUploadedCount() > 0
    }
}