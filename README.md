# OSM test
This README was created only for you to not read this README.
If you have read this README, then it is on your conscience.


# Дипломная работа
В данной работе была произведена попытка создать систему навигации общественного транспорта для г. Ашхабад на базе ОС Android.

Основным компонентом приложения является карта. В данной работе использовано API карт с открытым исходным кодом OpenStreeMap, а также специальная библиотека для работы с этими картами на Android – **OSMdroid**.

Для поиска мест и адресов на карте, в приложении будет использоваться геокодер geocode.map для **OpenStreetMap**. При обращении к геокодеру, приложение будет получать в ответ json файл. Данный файл далее парсится для получения нужных данных. Для парсинга json файлов в приложении используется библиотека **Retrofit**.

Для хранения списка автобусных остановок, их названий и координат, а также, для хранения списка автобусов и автобусных маршрутов, при разработке данного приложения использется база данных **SQLite**. Для взаимодействия с БД в данной работе используется библиотека **Room**.

Для нахождения местоположения пользователя на карте, используется компонент **Google Fused Location Provider API**.

Приложение написано с использованием шаблона проектирования архитектуры приложений MVVM (Model-View-ViewModel).

# Результаты
**Главный экран. Отображение карты города на экране смартфона.**
<br>
![image](https://github.com/DovletBegenjev/OSM_test/assets/56294209/2b84812e-53f1-401e-b26b-7904b0b53073)

**Работа полей поиска мест и адресов: а) раскрытие окна поиска при нажатии на поле поиска б) результаты поиска**
<br>
_а)_ ![image](https://github.com/DovletBegenjev/OSM_test/assets/56294209/f2c55750-722e-449a-a982-09aa5c8cb22e)
_б)_ ![image](https://github.com/DovletBegenjev/OSM_test/assets/56294209/cda1f42a-ad3c-4305-8ae8-7d874ae72b51)

**Отображения обычных и кластерных маркеров автобусных остановок на карте города после нажатия на кнопку отображения маркеров.**
<br>
![image](https://github.com/DovletBegenjev/OSM_test/assets/56294209/3fcc2e31-5f67-423b-a3e7-a6a4e1b357c6)

**Отображение информации об автобусной остановке при нажатии на маркер.**
<br>
![image](https://github.com/DovletBegenjev/OSM_test/assets/56294209/7918c29e-0d27-4ea4-bd4c-60c0c3948409)

**Кнопка навигации на местоположение пользователя.**
<br>
![image](https://github.com/DovletBegenjev/OSM_test/assets/56294209/4ccba405-fb8e-49d0-b9ff-895ad72e6810)

**Отображения маркера искомого места на карте.**
<br>
![image](https://github.com/DovletBegenjev/OSM_test/assets/56294209/7e53d371-8d2a-426a-a5e7-2b5360026a27)

**Отображение на карте автобусного маршрута.**
<br>
![image](https://github.com/DovletBegenjev/OSM_test/assets/56294209/64df7174-41d9-465a-a0be-64147feb2fc9)

**Отображения окна об автобусном маршруте.**
<br>
![image](https://github.com/DovletBegenjev/OSM_test/assets/56294209/9e9bd5f4-c065-40c9-81a6-19b81dd0f232)

**Окно автобусных маршрутов: а) отображение списка автобусных маршрутов б) поиск автобусных маршрутов**
<br>
_а)_ ![image](https://github.com/DovletBegenjev/OSM_test/assets/56294209/a4e370d0-a77a-494f-ad6b-c5a49200de09)
_б)_ ![image](https://github.com/DovletBegenjev/OSM_test/assets/56294209/388384a0-9d00-4295-84fa-4452040415f3)

**Отображение маршрута выбранного автобуса: а) показ автобусного маршрута на карте б) показ списка остановок, через которые проезжает выбранный автобус в) показ автобусной остановки при нажатии на элемент списка**
<br>
_а)_ ![image](https://github.com/DovletBegenjev/OSM_test/assets/56294209/e848609d-35c7-4826-992f-cf6e063a8e7e)
_б)_ ![image](https://github.com/DovletBegenjev/OSM_test/assets/56294209/248b4413-b940-4f3b-ae92-dccc91516017)
_в)_ ![image](https://github.com/DovletBegenjev/OSM_test/assets/56294209/b51bc3d5-905f-4a22-ad7d-a290461450bc)

**Окно автобусных остановок: а) отображение списка автобусных остановок б) поиск автобусных остановок**
<br>
_а)_ ![image](https://github.com/DovletBegenjev/OSM_test/assets/56294209/51f762b8-9947-4864-8599-5c32587e3c36)
_б)_ ![image](https://github.com/DovletBegenjev/OSM_test/assets/56294209/d59ea068-b55b-41be-b6dd-7be9679238a4)

**Отображение на карте выбранной автобусной остановки: а) показ автобусной остановки на карт б) показ списка автобусов, которые проезжают через выбранную автобусную остановку в) показ автобусного маршрута при нажатии на элемент списка**
<br>
_а)_ ![image](https://github.com/DovletBegenjev/OSM_test/assets/56294209/b7ee3780-c5e5-44d2-bfd7-016af78c5f48)
_б)_ ![image](https://github.com/DovletBegenjev/OSM_test/assets/56294209/3effcde2-8744-434d-88b2-0b7e6e79f818)
_в)_ ![image](https://github.com/DovletBegenjev/OSM_test/assets/56294209/874eb825-1f11-475e-bfc0-d816e89f94f2)

**Тестирование кнопки закрытия: а) до нажатия б) после нажатия**
<br>
_а)_ ![image](https://github.com/DovletBegenjev/OSM_test/assets/56294209/4fc89fe2-6290-45b2-978c-8ad9c1c4760a)
_б)_ ![image](https://github.com/DovletBegenjev/OSM_test/assets/56294209/e36c488f-f96a-45ec-9233-dda014caf163)
