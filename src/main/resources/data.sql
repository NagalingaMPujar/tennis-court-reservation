insert into guest(id, name) values(null, 'Roger Federer');
insert into guest(id, name) values(null, 'Rafael Nadal');
insert into guest(id, name) values(null, 'Lisa Rai');

insert into tennis_court(id, name) values(null, 'Roland Garros - Court Philippe-Chatrier');
insert into tennis_court(id, name) values(null, 'Roger Federer');

insert
    into
        schedule
        (id, start_date_time, end_date_time, tennis_court_id)
    values
        (null, '2020-12-20T20:00:00.0', '2020-02-20T21:00:00.0', 1);

insert into schedule
                (id, start_date_time, end_date_time, tennis_court_id)
            values
                (null, '2021-05-05T20:00:00.0', '2021-05-05T21:00:00.0', 2);
insert into schedule
                                (id, start_date_time, end_date_time, tennis_court_id)
                            values
                                (null, '2021-05-06T20:00:00.0', '2021-05-06T21:00:00.0', 2);


