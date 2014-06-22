package contenedor.apprade.Vista;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.pe.apprade.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;




public class PrincipalActivity extends FragmentActivity {

    private GoogleMap mMap; 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           
        setContentView(R.layout.activity_principal);
        setUpMapIfNeeded();
    }
  
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }
    private void setUpMapIfNeeded() {
        if (mMap == null) {            
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            
            if (mMap != null) {
                setUpMap();
            }
        }
    }
    
private void setUpMap() {
  mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
}