# Check Service Running with Broadcast

## 목표

1. 액티비티는 버튼을 통해 서비스를 실행 및 중단할 수 있다.
2. 액티비티가 서비스 중단 버튼을 누르지 않는다면 액티비티가 먼저 중단되어도 서비스는 실행되어야 한다.
3. 액티비티는 실행 중인 서비스와 통신할 수 있다.
4. 이전 액티비티가 서비스를 중단하지 않고 먼저 사라져도 다음 액티비티가 이전 액티비티가 생성한 서비스를 찾아 통신할 수 있어야 한다.

## 이슈

```
private boolean isMyServiceRunning(Class<?> serviceClass) {
    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
        if (serviceClass.getName().equals(service.service.getClassName())) {
            return true;
        }
    }
    return false;
}
```

위 코드 중 `manager.getRunningServices(Integer.MAX_VALUE)`는 더 이상 지원하지 않는다.

<img width="199" alt="1" src="https://github.com/49EHyeon42/Notification-Example-Android/assets/78364654/54cddd2f-a349-403d-88de-6240d56f5f6d">

위 그림과 같이 `Broadcast`를 이용해 서비스의 동작 여부를 확인하고 bind 하는 방식으로 리팩터링했다.

## 활용 방안

- 음악 재생

# 참고

[How to check if a service is running on Android?](https://stackoverflow.com/questions/600207/how-to-check-if-a-service-is-running-on-android)