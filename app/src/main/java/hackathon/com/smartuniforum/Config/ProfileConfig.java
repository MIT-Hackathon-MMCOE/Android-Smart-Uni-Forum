package hackathon.com.smartuniforum.Config;

import hackathon.com.smartuniforum.Models.ProfileModel;

/**
 * Created by aditya on 17/2/18.
 */

public class ProfileConfig {

    ProfileModel profileModel;
    public final void setProfileModel(ProfileModel profileModel){
        this.profileModel = profileModel;
    }
    public ProfileModel getProfileModel(){
        return this.profileModel;
    }
}
