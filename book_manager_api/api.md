# 系统中的响应码规范

| 响应码 | 说明          |
| ------ | ------------- |
| 6666   | 成功          |
| 8888   | 出错          |
| 7777   | 用户名不存在  |
| 9999   | 密码错误      |
| 10010  | token令牌失效 |

# 1.后台用户接口

## 1.1.用户登陆接口

请求地址:`/user/login`

请求方式:`POST`

请求数据:(QueryString的方式)

| 参数名称 | 参数类型 | 说明   |
| -------- | -------- | ------ |
| username | string   | 用户名 |
| password | string   | 密码   |

响应数据格式:

```json
{
    "code": 6666,//响应码
    "msg": "登陆成功"//响应信息
}
```

## 1.2.获取用户名接口

请求地址:`/user/userInfo`

请求方式:`GET`

请求数据:(headers请求头的方式)

| 参数名称     | 参数类型 | 说明      |
| ------------ | -------- | --------- |
| Authenticate | string   | token令牌 |

响应数据格式

```json
{
    "code":6666,//响应码
    "data":{"username":"admin"},//用户名
    "msg":"成功"//响应信息
}
```

# 2.后台院系接口

## 2.1.添加院系接口

请求地址:`/department/add`

请求方式:`POST`

请求数据:

| 参数名称    | 参数类型 | 说明     |
| ----------- | -------- | -------- |
| name        | string   | 院系名称 |
| create_date | string   | 创建时间 |

## 2.2.删除院系接口

请求地址:`/department/deleteDepartment`

请求方式:`GET`

请求数据:

| 参数名称 | 参数类型 | 说明   |
| -------- | -------- | ------ |
| id       | int      | 院系id |

## 2.3.分页获取院系信息

请求地址:`/department/getDepartmentList`

请求方式:`GET`

请求数据:

| 参数名称        | 参数类型 | 说明                                                   |
| --------------- | -------- | ------------------------------------------------------ |
| currentPage     | int      | 当前页数                                               |
| pageSize        | int      | 一页有多少个元素                                       |
| name            | String   | 院系名称(可以为空)                                     |
| startCreateDate | String   | 开始时间                                               |
| endCreateDate   | String   | 结束时间(开始时间和结束时间并存，要么都有，要么都没有) |

## 2.4.修改院系信息

请求地址:`/department/update`

请求方式:`POST`

请求数据:

| 参数名称    | 参数类型 | 说明     |
| ----------- | -------- | -------- |
| id          | int      | id号     |
| name        | String   | 院系名称 |
| create_date | String   | 创建时间 |

## 2.5.获取全部院系

请求地址:`/department/listAll`

请求方式:`GET`

# 3.后台图书接口

