package com.matius.indodess.ui.policy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PolicyViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PolicyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Selamat datang di platform INDODESS yang secara langsung dioperasikan oleh " +
                "PT. Buat TR PAM (“INDODESS”). INDODESS berkomitmen untuk bertanggung " +
                "jawab bedasarkan peraturan perundang-undangan tentang privasi yang berlaku di " +
                "Indonesia (“Undang-Undang Privasi”) dan berkomitmen penuh untuk menghormati hak " +
                "dan masalah privasi semua Member/Verfied Member yang terdaftar di platform kami.\n\n" +
                "Kebijakan Privasi berikut ini menjelaskan bagaimana INDODESS beserta syarat-syarat " +
                "penggunaan dari platform INDODESS yang tercantum dalam “Syarat & Ketentuan” dan " +
                "Informasi lain yang tercantum dalam situs INDODESS menetapkan dasar dalam " +
                "mengumpulkan, menyimpan, menggunakan, mengolah, menguasai, mentransfer, " +
                "mengungkapkan dan melindungi informasi Pribadi Member/Verified Member yang " +
                "INDODESS dapatkan dan kumpulkan dari Member/Verified Member yang terdiri dari " +
                "data pribadi Member/Verified Member, yang Member/Verified Member gunakan saat " +
                "melakukan pendaftaran, melakukan akses, dan mengunakan layanan pada platform INDODESS.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}