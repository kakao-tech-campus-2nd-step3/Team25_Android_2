package com.kakaotech.team25M.ui.companion

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kakaotech.team25M.R
import com.kakaotech.team25M.databinding.ActivityLiveCompanionBinding
import com.kakaotech.team25M.domain.model.Coordinates
import com.kakaotech.team25M.ui.status.CompanionCompleteDialog
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelManager
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class LiveCompanionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLiveCompanionBinding
    private val kakaoMapDeferred = CompletableDeferred<KakaoMap>()
    private val liveCompanionViewModel: LiveCompanionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLiveCompanionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startMapView()
        setupObserves()
        setLiveCompanionRecyclerViewAdapter()
        setClickListener()
        navigateToPrevious()
    }

    private fun startMapView() {
        binding.mapView.start(
            createMapLifeCycleCallback(),
            createMapReadyCallback(),
        )
    }

    private fun createMapLifeCycleCallback(): MapLifeCycleCallback {
        return object : MapLifeCycleCallback() {
            override fun onMapDestroy() {}

            override fun onMapError(error: Exception?) {}
        }
    }

    private fun createMapReadyCallback(): KakaoMapReadyCallback {
        return object : KakaoMapReadyCallback() {
            override fun onMapReady(kakaoMap: KakaoMap) {
                kakaoMapDeferred.complete(kakaoMap)
            }
        }
    }

    private fun updateLabelsToMap(labelManager: LabelManager, latLng: LatLng) {
        val styles =
            LabelStyles.from(
                LabelStyle.from(R.drawable.marker).setZoomLevel(8),
            )

        val labelOptions =
            LabelOptions.from(
                LatLng.from(latLng),
            )
                .setStyles(styles)

        labelManager.layer?.removeAll()
        labelManager.layer?.addLabel(labelOptions)
    }

    private fun updateMapLocation(kakaoMap: KakaoMap, coordinates: Coordinates) {
        val newLatLng = LatLng.from(coordinates.latitude, coordinates.longitude)
        val labelManager = kakaoMap.labelManager

        kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(newLatLng))

        if (labelManager != null) updateLabelsToMap(labelManager, newLatLng)
    }

    private fun setupObserves() {
        collectAccompanyInfo()
        collectCoordinates()
        collectStatusMessage()
    }

    private fun collectAccompanyInfo() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                liveCompanionViewModel.accompanyInfo.collectLatest { accompanyInfo ->
                    val name = accompanyInfo.reservationInfo.patient.patientName

                    liveCompanionViewModel.updateCoordinates(
                        accompanyInfo.latitude,
                        accompanyInfo.longitude
                    )

                    updateManagerStatus(name)
                }
            }
        }
    }

    private fun collectCoordinates() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                val kakaoMap = kakaoMapDeferred.await()

                liveCompanionViewModel.coordinates.collectLatest { coordinates ->
                    updateMapLocation(kakaoMap, coordinates)
                }
            }
        }
    }

    private fun collectStatusMessage() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                liveCompanionViewModel.statusMessages.collectLatest { statusMessages ->
                    (binding.liveCompanionRecyclerView.adapter as? LiveCompanionRecyclerViewAdapter)?.submitList(
                        statusMessages
                    )
                    binding.liveCompanionRecyclerView.visibility =
                        if (statusMessages.isEmpty()) View.GONE else View.VISIBLE
                }
            }
        }
    }

    private fun updateManagerStatus(name: String) {
        binding.patientNameTextView.text = name
    }

    private fun setLiveCompanionRecyclerViewAdapter() {
        val adapter = LiveCompanionRecyclerViewAdapter()
        binding.liveCompanionRecyclerView.adapter = adapter
        binding.liveCompanionRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setClickListener() {
        setCompanionStatusInputFormClickListener()
        setCompanionCompleteBtnClickListener()
    }

    private fun setCompanionStatusInputFormClickListener() {
        binding.companionStatusInputBtn.setOnClickListener {
            val formattedDate = getCurrentFormattedDateTime()
            val message = formattedDate + " " + binding.companionStatusInputEditText.text

            liveCompanionViewModel.addStatusMessage(message)

            binding.companionStatusInputEditText.text.clear()
        }
    }

    // 다이얼 로그에 예약 정보도 같이 전달
    private fun setCompanionCompleteBtnClickListener() {
        binding.completeCompanionBtn.setOnClickListener {
            val reservationInfo = liveCompanionViewModel.accompanyInfo.value.reservationInfo
            val companionCompleteDialog = CompanionCompleteDialog.newInstance(reservationInfo)

            stopLocationService()
            companionCompleteDialog.show(supportFragmentManager,"CompanionCompleteDialog")
        }
    }

    private fun stopLocationService(){
        //val intent = Intent(this, LocationUpdateService::class.java)
        this.stopService(intent)
    }

    private fun getCurrentFormattedDateTime(): String {
        val currentTime = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("yy.MM.dd HH:mm", Locale.KOREAN)

        return dateFormat.format(currentTime)
    }

    private fun navigateToPrevious() {
        binding.mapPreviousBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    @Override
    override fun onResume() {
        super.onResume()
        binding.mapView.resume()
    }

    @Override
    public override fun onPause() {
        super.onPause()
        binding.mapView.pause()
    }
}
