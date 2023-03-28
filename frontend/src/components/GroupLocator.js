import { useContext, useEffect, useState } from 'react';
import { MapContainer, Marker, Popup, TileLayer } from 'react-leaflet';
import { context } from '../Store';
import { BACKEND_URL } from '../Constants';

function GroupLocator() {
  const [state,] = useContext(context);
  const [lat,] = useState(44.651070);
  const [lng,] = useState(-63.582687);
  const [zoom,] = useState(13);
  const [markers, setMarkers] = useState([]);
  const [position,] = useState([lat, lng]);
  useEffect(() => {
    fetch(BACKEND_URL + 'fetch/location/' + state.group.id + "?username=" + state.username, {
      method: 'GET'
    })
      .then((response) => response.json())
      .then(async (data) => {
        console.log('Success:', data);
        data[data.length] = {
          latitude: state.latitude,
          longitude: state.longitude,
          username: 'You'
        };
        data[data.length] = {
          latitude: state.latitude + 10,
          longitude: state.longitude,
          username: 'You'
        };
        await setMarkers(data);
      })
      .catch((error) => {
        console.error('Error:', error);
      });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <div className='map'>
      <MapContainer center={position} zoom={zoom} style={{ height: '70vh', width: "100%", zIndex: 94 }}>
        <TileLayer
          attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
          url='http://{s}.tile.osm.org/{z}/{x}/{y}.png'
        />
        {markers.map((marker, index) => (
          <Marker key={index} position={[marker.latitude, marker.longitude]}>
            <Popup>
              <span>
                {marker.username}
              </span>
            </Popup>
          </Marker>
        ))}
      </MapContainer>
    </div>
  );
}
export default GroupLocator;