/**
 * An Image Picker Plugin for Cordova/PhoneGap.
 */
package com.synconset;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

public class ImagePicker extends CordovaPlugin {
	public static String TAG = "ImagePicker";
	static final int OPEN_MEDIA_PICKER = 1;
	 
	private CallbackContext callbackContext;
	private JSONObject params;
	 
	public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
		 this.callbackContext = callbackContext;
		 this.params = args.getJSONObject(0);
		if (action.equals("getPictures")) {
			Intent intent = new Intent(cordova.getActivity(), Gallery.class);
			/** int max = 20;
			int desiredWidth = 0;
			int desiredHeight = 0;
			int quality = 100;
			if (this.params.has("maximumImagesCount")) {
				max = this.params.getInt("maximumImagesCount");
			}
			if (this.params.has("width")) {
				desiredWidth = this.params.getInt("width");
			}
			if (this.params.has("height")) {
				desiredHeight = this.params.getInt("height");
			}
			if (this.params.has("quality")) {
				quality = this.params.getInt("quality");
			}
			intent.putExtra("MAX_IMAGES", max);
			intent.putExtra("WIDTH", desiredWidth);
			intent.putExtra("HEIGHT", desiredHeight);
			intent.putExtra("QUALITY", quality); **/
			if (this.cordova != null) {
				//this.cordova.startActivityForResult((CordovaPlugin) this, intent, 0);
				
				// Set the title
				intent.putExtra("title","Select media");
				// Mode 1 for both images and videos selection, 2 for images only and 3 for videos!
				intent.putExtra("mode",1); 
				intent.putExtra("maxSelection",3); // Optional
				this.cordova.startActivityForResult((CordovaPlugin) this, intent, OPEN_MEDIA_PICKER);
				//startActivityForResult(intent,OPEN_MEDIA_PICKER);
			}
		}
		return true;
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && data != null) {
			//ArrayList<String> fileNames = data.getStringArrayListExtra("MULTIPLEFILENAMES");
			ArrayList<String> fileNames = data.getStringArrayListExtra("result");

			JSONArray res = new JSONArray(fileNames);
			this.callbackContext.success(res);
		} else if (resultCode == Activity.RESULT_CANCELED && data != null) {
			String error = data.getStringExtra("ERRORMESSAGE");
			this.callbackContext.error(error);
		} else if (resultCode == Activity.RESULT_CANCELED) {
			JSONArray res = new JSONArray();
			this.callbackContext.success(res);
		} else {
			this.callbackContext.error("No images selected");
		}
	}
}
