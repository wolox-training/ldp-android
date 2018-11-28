package ar.com.wolox.android.example.ui.login;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import ar.com.wolox.android.example.model.User;
import ar.com.wolox.android.example.network.UserService;
import ar.com.wolox.android.example.utils.Constants;
import ar.com.wolox.wolmo.core.util.SharedPreferencesManager;
import ar.com.wolox.wolmo.networking.retrofit.RetrofitServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class LoginPresenterTest {

    private static final String TEST_EMAIL = "test_email@email.com";
    private static final String TEST_PASSWORD = "passwd";

    @Mock private ILoginView mILoginView;
    @Mock private SharedPreferencesManager mSharedPreferencesManager;
    @Mock private RetrofitServices mRetrofitServices;

    @Mock private UserService mUserService;
    @Mock private Response<List<User>> mSuccessResponse;
    @Mock private Response<List<User>> mSuccessUserResponse;
    @Mock private Response mFailedResponse;

    private List<User> mTestUsers;

    private LoginPresenter mLoginPresenter;

    @Before
    public void createInstances() {
        MockitoAnnotations.initMocks(this);

        when(mRetrofitServices.getService(UserService.class)).thenReturn(mUserService);

        when(mSuccessResponse.body()).thenReturn(new LinkedList<>());
        when(mSuccessResponse.isSuccessful()).thenReturn(true);

        mTestUsers = new LinkedList<>();
        User user = new User();
        user.setEmail(TEST_EMAIL);
        mTestUsers.add(user);

        when(mSuccessUserResponse.body()).thenReturn(mTestUsers);
        when(mSuccessUserResponse.isSuccessful()).thenReturn(true);

        when(mFailedResponse.isSuccessful()).thenReturn(false);

        mLoginPresenter = new LoginPresenter(mSharedPreferencesManager, mRetrofitServices);
        mLoginPresenter.attachView(mILoginView);
    }

    @Test
    public void checkUserIsLoggedIn() {
        mLoginPresenter.isUserLoggedIn();
        verify(mSharedPreferencesManager).get(Constants.UserCredentials.USER_LOGGED_IN, false);
    }

    @Test
    public void emailIsStore() {
        mLoginPresenter.storeEmail(TEST_EMAIL);
        verify(mSharedPreferencesManager).store(Constants.UserCredentials.USER_EMAIL, TEST_EMAIL);
    }

    @Test
    public void emailIsReadAndUpdatesView() {
        when(mSharedPreferencesManager.get(Constants.UserCredentials.USER_EMAIL, "")).thenReturn(TEST_EMAIL);
        mLoginPresenter.loadEmail();
        verify(mSharedPreferencesManager).get(Constants.UserCredentials.USER_EMAIL, "");
        verify(mILoginView).setEmail(TEST_EMAIL);
    }

    @Test
    public void emailNotPersistedAndUpdatesView() {
        when(mSharedPreferencesManager.get(Constants.UserCredentials.USER_EMAIL, "")).thenReturn("");
        mLoginPresenter.loadEmail();
        verify(mILoginView).setEmail("");
        verify(mSharedPreferencesManager, atLeastOnce()).get(Constants.UserCredentials.USER_EMAIL, "");
    }

    @Test
    public void loginWithWrongEmailAndPassword() {
        Call<List<User>> callListMock = mock(Call.class);
        when(mUserService.login(anyString(), anyString())).thenReturn(callListMock);

        doAnswer(invocation -> {
            Callback callback = invocation.getArgument(0);
            callback.onResponse(callListMock, mSuccessResponse);
            return null;
        }).when(callListMock).enqueue(any(Callback.class));

        mLoginPresenter.login(TEST_EMAIL, TEST_PASSWORD);

        verify(mILoginView, atLeastOnce()).showProgress();
        verify(mILoginView, atLeastOnce()).hideProgress();
        verify(mILoginView).onWrongEmailOrPassword();
    }

    @Test
    public void loginSucceedsWithCorrectEmailAndPassword() {
        Call<List<User>> callListMock = mock(Call.class);

        when(mUserService.login(anyString(), anyString())).thenReturn(callListMock);

        doAnswer(invocation -> {
            Callback callback = invocation.getArgument(0);
            callback.onResponse(callListMock, mSuccessUserResponse);
            return null;
        }).when(callListMock).enqueue(any(Callback.class));

        mLoginPresenter.login(TEST_EMAIL, TEST_PASSWORD);

        verify(mILoginView, atLeastOnce()).showProgress();
        verify(mILoginView, atLeastOnce()).hideProgress();
        verify(mSharedPreferencesManager).store(Constants.UserCredentials.USER_EMAIL, TEST_EMAIL);
        verify(mILoginView).onUserLoggedIn();
    }

    @Test
    public void loginFailedWhenNoConnection() {
        Call<List<User>> callListMock = mock(Call.class);
        when(mUserService.login(anyString(), anyString())).thenReturn(callListMock);

        doAnswer(invocation -> {
            Callback callback = invocation.getArgument(0);
            callback.onFailure(callListMock, new UnknownHostException());
            return null;
        }).when(callListMock).enqueue(any(Callback.class));

        mLoginPresenter.login(TEST_EMAIL, TEST_PASSWORD);

        verify(mILoginView, atLeastOnce()).showProgress();
        verify(mILoginView, atLeastOnce()).hideProgress();
        verify(mILoginView).showNoConnectionError();
    }

    @Test
    public void loginFailedUnexpectedly() {
        Call<List<User>> callListMock = mock(Call.class);
        when(mUserService.login(anyString(), anyString())).thenReturn(callListMock);

        doAnswer(invocation -> {
            Callback callback = invocation.getArgument(0);
            callback.onFailure(callListMock, new Exception());
            return null;
        }).when(callListMock).enqueue(any(Callback.class));

        mLoginPresenter.login(TEST_EMAIL, TEST_PASSWORD);

        verify(mILoginView, atLeastOnce()).showProgress();
        verify(mILoginView, atLeastOnce()).hideProgress();
        verify(mILoginView).showUnexpectedError();
    }

    @Test
    public void loginNotSucceedsErrorGettingResponse() {
        Call<List<User>> callListMock = mock(Call.class);
        when(mUserService.login(anyString(), anyString())).thenReturn(callListMock);

        doAnswer(invocation -> {
            Callback callback = invocation.getArgument(0);
            callback.onResponse(callListMock, mFailedResponse);
            return null;
        }).when(callListMock).enqueue(any(Callback.class));

        mLoginPresenter.login(TEST_EMAIL, TEST_PASSWORD);

        verify(mILoginView, atLeastOnce()).showProgress();
        verify(mILoginView, atLeastOnce()).hideProgress();
        verify(mILoginView).showUnexpectedError();
    }
}