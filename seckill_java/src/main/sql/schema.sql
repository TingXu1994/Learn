create database seckill;
use seckill;

-- 创建库存表
create table seckill(
	'seckill_id' bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
    'name' varchar(120) NOT NULL COMMENT '商品名词',
    'number' int NOT NULL COMMENT '库存数量'
    'start_time' timestamp NOT NULL	COMMENT '秒杀开始时间'
    'end_time' timestamp NOT NULL COMMENT '秒杀结束时间'
    'create_time' timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀创建时间'
    PRIMARY KEY (seckill_id),
    key idx_start_time(start_time),
    key idx_end_time(end_time),
    key idx_create_time(create_time),
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

-- 初始化数据
insert into seckill(name,number,start_time,end_time)
values
	  ('1000元秒杀iphone6',100,'2016-01-01 00:00:00','2016-01-02 00:00:00'),
      ('800元秒杀ipad',200,'2016-01-01 00:00:00','2016-01-02 00:00:00'),
      ('6600元秒杀mac book pro',300,'2016-01-01 00:00:00','2016-01-02 00:00:00'),
      ('7000元秒杀iMac',400,'2016-01-01 00:00:00','2016-01-02 00:00:00');
      
create table success_get(
	'seckill_id' bigint NOT NULL COMMENT '商品库存id',
    'user_phone' bigint NOT NULL COMMENT '用户手机号',
    'state' tinyint NOT NULL DEFAULT -1 COMMENT '状态：-1：无效，0：成功；1：已付款'
    'create_time' timestamp NOT NULL COMMENT '秒杀创建时间'
    PRIMARY KEY(seckill_id,user_phone),/*联合主键*/
    key idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀库存表'
	
mysql -uroot -p 