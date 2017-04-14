SELECT
    id,
    smlr
FROM
(
select c.id , c.pattern <-> b.pattern as smlr
	from pat c,
	(
select shuffle_pattern(a.pattern) as pattern,pattern2signature(a.pattern) as signature
 from (select jpeg2pattern(pg_read_binary_file('image/000.jpg'))) a
)
b
    order by c.signature <-> b.signature
	limit 100
) x
ORDER BY x.smlr ASC 
LIMIT 10;


SELECT id,smlr FROM ( select c.id , c.pattern <-> shuffle_pattern(jpeg2pattern(pg_read_binary_file('image/000.jpg'))) as smlr from pat c order by c.signature <-> pattern2signature(jpeg2pattern(pg_read_binary_fi('image/000.jpg')))
	limit 100
) x
ORDER BY x.smlr ASC
LIMIT 10;
