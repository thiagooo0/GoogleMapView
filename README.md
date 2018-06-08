make your InfoWindow of GoogleMap alive.

### Depend it
[i uss the jitpack](https://jitpack.io/#thiagooo0/GoogleMapView/v1.0.6).

so

* add it in your root build.gradle
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
* add the dependency
```
dependencies {
	        implementation 'com.github.thiagooo0:GoogleMapView:v1.0.6'
	}
```

### Use it
you can see the simple at app module.

1, use the GMapView as the GoogleMapView in your xml
2, show a InfoWindow
```java
//you should keep the InfoWindow instance to hide it.
mapView.showInfoWindow(new InfoWIndow());
``` 
3, hide a InfoWindow
```java
mapView.dismissInfoWindow(infoWindow);
```
