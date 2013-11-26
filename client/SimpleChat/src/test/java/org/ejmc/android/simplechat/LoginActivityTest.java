package org.ejmc.android.simplechat;

/**
 * Created with IntelliJ IDEA.
 * User: laura
 * Date: 25/11/13
 * Time: 16:56
 * To change this template use File | Settings | File Templates.
 */



import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


public class LoginActivityTest {
    public void shouldHaveProperAppName() throws Exception{
        String appName = new LoginActivity().getResources().getString(R.string.app_name);
        assertThat(appName, equalTo("prueba1"));
    }
}
