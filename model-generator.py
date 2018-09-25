#!/usr/bin/python3

type_model_interface=0
type_model_class=1
type_builder_interface=2
type_builder_class=3
type_manager_interface=4
type_manager_class=5
type_controller=6
type_repository=7
type_request=8
type_response=9
type_assembler=10
type_security_rest = 11
type_factory_interface = 12
type_factory_class = 13
type_security_core=14


def model_interface(name,fields,super):
    content = ''
    pack_name = package(type_model_interface)
    content = append(content,pack(pack_name))

    name = capitalize(name)
    super = capitalize(super)
    name = 'I'+name
    super = 'I%sEntity' % super

    content = append(content,claim('interface',name,super,None))
    print(fields)
    for field in fields:
        get = '%s get%s()' %(field[0],field[1].capitalize())
        content = append(content,indent(get,floor=1))

    content = append(content,close())

    directory = dir(path(type_model_interface),pack_name)
    write(directory,name,content)


def model_class(name,fields,super):
    content = ''
    pack_name = package(type_model_class)
    content = append(content,pack(pack_name))
    content = append(content,using())

    name = capitalize(name)
    super = capitalize(super)
    super = '%sEntity' % super
    interface = 'I'+name

    content =append(content,annotation(name.lower()))
    content = append(content,claim('class',name,super,interface))
    content = append(content,constructor(name))


    for field in fields:
        annotations = []
        if len(field) >=3:
            annotations.append('Comment("%s")' % field[2])

        column_name = field[3] if len(field)>=4 else field[1]
        annotations.append('Column(name="%s")' % column_name)
        content=append(content,field_claim(field,annotations=annotations))

    for field in fields:
        content=append(content,field_get_set(field))

    content = append(content,close())


    directory = dir(path(type_model_class),pack_name)
    write(directory,name,content)


def builder_interface(name,fields,super):
    model = capitalize(name)
    super = capitalize(super)
    model_interface = 'I%s' % model
    interface = 'I%sBuilder' % model
    super_interface = 'I%sBuilder<%s>' % (super,model_interface)

    content = ''

    pack_name = package(type_builder_interface)
    content = append(content,pack(pack_name))

    content = append(content,imports(['%s.%s' % (package(type_model_interface),model_interface)]))
    content = append(content,claim('interface',interface,super_interface,None))

    for field in fields:
        content = append(content,method_claim(interface,field[1],[field]))
    content=append(content,line())
    content = append(content,close())

    directory = dir(path(type_builder_interface),pack_name)
    write(directory,interface,content)

def builder_class(name,fields,super):
    name = capitalize(name)
    super = capitalize(super)
    model_class = name
    model_interface = 'I%s' % name
    builder_class = '%sBuilder' % name
    builder_interface = 'I%sBuilder' % name
    super = '%sCreupdate<%s,%s>' % (super,builder_interface,model_interface)

    content = ''
    pack_name = package(type_builder_class)
    content = append(content,pack(pack_name))

    content=append(content,imports(['%s.%s' % (package(type_model_class),model_class),'%s.%s' % (package(type_model_class),model_interface)]))
    content =append(content,claim('class',builder_class,super,builder_interface))

    for field in fields:
        content=append(content,field_claim(field))

    for field in fields:
        method = method_claim(builder_interface,field[1],[field],access='public',annotations=['Override'],end='{')
        method = append(method,indent('this.%s=%s' % (field[1],field[1]),floor=2))
        method = append(method,indent('return self()',floor=2))
        method = append(method,close(floor=1))
        content=append(content,method)

    content = append(content,method_claim(model_interface,'newInstance',[],access='protected',annotations=['Override'],end='{'))
    content = append(content,indent('return new %s()' % model_class,floor=2))
    content = append(content,close(floor=1))

    arg = 'entity'
    method = 'apply'
    lam = model_class.lower()[0:1]
    content = append(content,method_claim('void',method,[(model_interface,arg)],access='protected',annotations=['Override'],end='{'))
    content = append(content,indent('super.%s(%s)' % (method,arg),floor=2))
    content = append(content,indent('this.<%s>tryCast(%s).ifPresent' % (model_class,arg),floor=2,end='('))
    content = append(content,indent('%s -> {' % lam,floor=4,end=''))
    for field in fields:
        set = ''
        set = append(set,indent('if(%s!=null)' % field[1],floor=5,end=''))
        set = append(set,indent('%s.set%s(%s)' % (lam,capitalize(field[1]),field[1]),floor=6)+line())

        content = append(content,set)
    content = append(content,close(floor=4))
    content = append(content,indent(')',floor=2))
    content = append(content,close(floor=1))
    content = append(content,close())

    directory = dir(path(type_builder_class),pack_name)
    write(directory,builder_class,content)


def repository_interface(name,fields,super):
    name = capitalize(name)
    super = capitalize(super)
    model_class = name
    model_interface = 'I%s' % name
    repository_interface = '%sRepository' % name
    super = 'I%sRepository<%s>' % (super,model_class)

    content = ''
    pack_name = package(type_repository)
    content = append(content,pack(pack_name))
    content = append(content,imports(['org.springframework.stereotype.Repository','%s.%s' %(package(type_model_class),model_class),'%s.%s' %(package(type_model_interface),model_interface)]))

    content = append(content,claim('interface',repository_interface,super,None,annotations=['Repository']))

    for field in fields:
        content = append(content,method_claim('boolean','existsBy%s' % name,[field]))
        content = append(content,method_claim(model_interface,'findBy%s' % name,[field]))
        content = append(content,method_claim('void','deleteBy%s' % name,[field])+line())

    content = append(content,close())

    directory = dir(path(type_repository),pack_name)
    write(directory,repository_interface,content)


def manager_interface(name,fields,super):
    name = capitalize(name)
    super = capitalize(super)
    model_interface = 'I%s' % name
    super = 'I%sManager<%s>' % (super,model_interface)
    manager_interface = 'I%sManager' % name

    content = ''
    pack_name = package(type_manager_interface)
    content = append(content,pack(pack_name))

    content = append(content,imports(['%s.%s' % (package(type_model_interface),model_interface)]))

    content = append(content,claim('interface',manager_interface,super,None))

    for field in fields:
        content = append(content,method_claim('void','change%s' % name,[field]))
        content = append(content,method_claim('boolean','existsBy%s' % name,[field]))
        content = append(content,method_claim(model_interface,'findBy%s' % name,[field]))
        content = append(content,method_claim('void','deleteBy%s' % name,[field])+line())

    content = append(content,close())

    directory = dir(path(type_manager_interface),pack_name)
    write(directory,manager_interface,content)

def request(name,fields,super):
    name = capitalize(name)
    super = capitalize(super)
    super = '%sRequest' % super
    request = '%sRequest' % name

    content = ''
    pack_name = package(type_request)
    content = append(content,pack(pack_name))

    content = append(content,imports(['java.util.Optional']))
    content = append(content,claim('class',request,super,None))

    for field in fields:
        content = append(content,field_claim(field))

    for field in fields:
        content=append(content,field_get_set(field,optional=True,override=False))

    content = append(content,close())

    directory = dir(path(type_request),pack_name)
    write(directory,request,content)

def response(name,fields,super):
    name = capitalize(name)
    super = capitalize(super)
    super = '%sResponse' % super
    response = '%sResponse' % name

    content = ''
    pack_name = package(type_response)
    content = append(content,pack(pack_name))

    content = append(content,claim('class',response,super,None))

    for field in fields:
        content = append(content,field_claim(field))

    for field in fields:
        content=append(content,field_get_set(field,override=False))

    content = append(content,close())

    directory = dir(path(type_response),pack_name)
    write(directory,response,content)


def controller(name):
    name = capitalize(name)
    model_interface = 'I%s' % name
    response = '%sResponse' % name
    builder_interface = 'I%sBuilder' % name
    request = '%sRequest' % name
    controller = '%sController' % name
    super = 'BaseController<%s,%s,%s,%s>' % (model_interface,response,builder_interface,request)
    mapping = 'REST_BASE_MAPPING+REST_%s_MAPPING' % name.upper()


    content = ''
    pack_name = package(type_controller)
    content = append(content,pack(pack_name))
    content = append(content,imports(['%s.%s' % (package(type_model_interface),model_interface),'%s.%s' % (package(type_response),response),'%s.%s' % (package(type_builder_interface),builder_interface),'%s.%s' % (package(type_request),request),'org.springframework.web.bind.annotation.*','static com.nationalchip.iot.security.configuration.RestMappingConstant.*']))

    content = append(content,claim('class',controller,super,None,annotations=['RestController','RequestMapping(value=%s)' % mapping]))


    content = append(content,close())
    directory = dir(path(type_controller),pack_name)
    write(directory,controller,content)


def assembler(name,fields,super):
    name = capitalize(name)
    super = capitalize(super)
    model_interface = 'I%s' % name
    response = '%sResponse' % name
    builder_interface = 'I%sBuilder' % name
    request = '%sRequest' % name
    controller = '%sController' % name
    assembler = '%sAssembler' % name
    super = '%sAssembler<%s,%s,%s,%s>' % (super,model_interface,response,builder_interface,request)

    content = ''
    pack_name = package(type_assembler)
    content = append(content,pack(pack_name))
    content = append(content,imports(['%s.%s' % (package(type_model_interface),model_interface),'%s.%s' % (package(type_builder_interface),builder_interface),'%s.%s' % (package(type_controller),controller),'org.springframework.stereotype.*']))

    content = append(content,claim('class',assembler,super,None,annotations=['Component']))
    content = append(content,constructor(assembler,super = 'super(%s.class,%s.class)' % (controller,response)))

    content = append(content,method_claim(builder_interface,'builder',[],access='protected',annotations=['Override'],end='{'))
    content = append(content,indent('return getBuilderFactory().%s()' % name.lower(),floor=2))
    content = append(content,close(floor=1))


    arg = 'response'
    entity = 'entity'
    content = append(content,method_claim(response,'toResource',[(model_interface,entity)],access='public',annotations=['Override'],end='{'))
    content = append(content,indent('%s %s = super.toResource(%s)' % (response,arg,entity),floor=2))
    for field in fields:
        content = append(content,indent('%s.set%s(%s.get%s())' % (arg,capitalize(field[1]),entity,capitalize(field[1])),floor=2))
    content = append(content,indent('return %s' % arg,floor=2))
    content = append(content,close(floor=1))

    arg = 'request'
    builder = 'builder'
    content = append(content,method_claim(builder_interface,'fromRequest',[(request,arg)],access='public',annotations=['Override'],end='{'))
    content = append(content,indent('%s %s = super.fromRequest(%s)' % (builder_interface,builder,arg),floor=2))
    for field in fields:
        content = append(content,indent('%s.get%s().ifPresent(%s -> %s.%s(%s))' % (arg,capitalize(field[1]),field[1],builder,field[1],field[1]),floor=2))
    content = append(content,indent('return %s' % builder,floor=2))
    content = append(content,close(floor=1))

    content = append(content,close())
    directory = dir(path(type_assembler),pack_name)
    write(directory,assembler,content)

def mapping(name,map=None):
    constant = 'RestMappingConstant'

    if not map:
        map = name.lower()

    directory = dir(path(type_security_rest),package(type_security_rest))

    content =  'public static final String REST_%s_MAPPING = "/%s"' % (name.upper(),map)
    contents = [indent(content,floor=1)]

    add(directory,constant,contents=contents)

def factory(name):

    model = name.lower()
    builder_interface = 'I%sBuilder' % capitalize(name)
    builder_class = '%sBuilder' % capitalize(name)


    file ='IBuilderFactory'
    directory = dir(path(type_builder_interface),package(type_factory_interface))
    contents = [method_claim(builder_interface,model)]
    add(directory,file,contents=contents)


    file ='BuilderFactory'
    directory = dir(path(type_builder_class),package(type_factory_class))
    content = line()+method_claim(builder_interface,model,access='public',annotations=['Override'],end='{')
    content = append(content,indent('return new %s()' % builder_class,floor=2)+close(floor=1))
    add(directory,file,contents=[content])


def authority(name,model=''):

    name = name.upper()

    if model == None or model == '':
        model = name

    directory = dir(path(type_security_core),package(type_security_core))
    claim = 'public static final String'
    content = []
    content.append(indent('%s EN_%s = "%s"' % (claim,name,model),floor=1))
    ops = ['READ','CREATE','DELETE','UPDATE']
    for op in ops:
        content.append(indent('%s AUTH_%s_%s = OP_%s + SEPARATOR + EN_%s' % (claim,op,name,op,model),floor=1))
    file = 'Authority'

    add(directory,file,contents=content,reverse=False)

def pack(package):
    return indent('package %s' % package)+line()

def claim(type,name,super,interface,annotations=[]):
    an = anno(annotations,floor=0)

    return an+(indent('public %s %s extends %s implements %s{' %(type,name,super,interface),end='') if interface else indent('public %s %s extends %s{' %(type,name,super),end=''))+line()


def close(floor=0):
    return indent('}',floor=floor,end='')+line()

def field_get_set(field,optional=False,override=True):
    content=''
    field_type = field[0]
    field_name = field[1]

    content=append(content,getter(field_type,field_name,optional=optional,override=override))
    content=append(content,setter(field_type,field_name))
    return content

def line():
    return '\n'


def field_claim(field,access='private',annotations=[]):

    an = anno(annotations)

    content = indent('%s %s %s' %(access,field[0],field[1]),floor=1)
    return  an+content+line()


def method_claim(type,name,args=[],access=None,annotations=[],floor=1,end=';'):
    params = ''
    for arg in args:
        params=params+'%s %s,' % (arg[0],arg[1])
    params=params[0:len(params)-1]

    an = anno(annotations)

    return an + (indent('%s %s %s(%s)' % (access,type,name,params),floor=floor,end=end) if access else indent('%s%s %s(%s)' % (an,type,name,params),floor=floor,end=end))

def anno(annotations,floor=1):
    an = ''
    for annotation in annotations:
        an = append(an,indent('@%s' % annotation,floor=floor,end=''))

    return an

def getter(type,name,override=True,optional=False):
    ov = ['Override'] if override else []
    claim = method_claim('Optional<%s>' % type if optional else type,'get%s' % capitalize(name),[],access='public',annotations=ov,end='{')
    return claim + indent( 'return %s' % ('Optional.ofNullable(%s)' % name if optional else  name),floor=2)+close(floor=1)


def setter(type,name):
    claim = method_claim('void','set%s' % capitalize(name),[(type,name)],access='public',end='{')
    return claim + indent('this.%s=%s' % (name,name),floor=2)+close(floor=1)
#TODO:　待重构
def annotation(name):
    return indent('@MappedSuperclass',end='')+indent('@Entity',end='')+indent('@Table(name="%s")' % name,end='')

def using():
    return imports(['com.nationalchip.iot.data.annotation.Comment','javax.persistence.*'])

def imports(classz):
    content = ''

    for cls in classz:
        content=append(content,indent('import %s' % cls))
    content = append(content,line())

    return content


def constructor(name,super=''):
    return method_claim('',name,[],access='public',end='{') + indent(super,floor=2,end=';' if super else '') + close(floor=1)

def write(path,name,content,mode = 'w+'):
    file = '%s/%s.java' % (path,name)
    with open(file,mode) as writer:
        writer.write(content)

def add(path,name,contents=[],reverse=True):
    file = '%s/%s.java' % (path,name)

    lines=[]
    with open(file,'r') as reader:
        lines = reader.readlines()
    if reverse:
        contents.reverse()
    for content in contents:
        if content in lines:
            lines.remove(content)
        lines.insert(-2,content)
    lines.insert(-2,line())


    write(path,name,''.join(lines))

def append(content,add):
    return content+add


def indent(expression,floor=0,end=';'):
    for i in range(floor):
        expression='    '+expression
    return expression+end+'\n'


def capitalize(str):
    return str.lower().capitalize()

def package(type):
    prefix = 'com.nationalchip.iot.'

    subfix =''
    if type in [type_model_class,type_model_interface]:
        subfix = 'data.model'
    elif type in [type_builder_class, type_builder_interface, type_factory_interface, type_factory_class]:
        subfix = 'data.builder'
    elif type in [type_manager_class,type_manager_interface]:
        subfix = 'data.manager'
    elif type == type_controller:
        subfix = 'rest.controller'
    elif type == type_repository:
        subfix = 'data.repository'
    elif type == type_request or type == type_response or type == type_assembler :
        subfix = 'rest.resource'
    elif type == type_security_rest:
        subfix = 'security.configuration'
    elif type == type_security_core:
        subfix ='security.authority'

    return prefix+subfix

def dir(base,package):
    return '%s/%s' % (base,package.replace('.','/'))


def path(type):
    prefix = ''

    if type in [type_manager_interface,type_builder_interface,type_model_interface,type_factory_interface]:
        prefix = 'iot-data/iot-data-api'
    elif type in [type_manager_class, type_builder_class, type_model_class, type_repository, type_factory_class]:
        prefix='iot-data/iot-data-jpa'
    elif type in [type_controller,type_request,type_response,type_assembler]:
        prefix = 'iot-rest'
    elif type == type_security_rest:
        prefix = 'iot-security/iot-security-rest'
    elif type == type_security_core:
        prefix = 'iot-security/iot-security-core'

    return  prefix+'/src/main/java'


def test_append(str,content):
    str = str + content


#处理restmappingconstant
#处理builderfactory
if __name__ == '__main__':
    names = ['User','Role','Asset',"Document","News","Product"]
    # super = 'Named'
    # fields=[('String','alias','别名'),('int','count','数量','num')]
    #
    #
    # model_interface(name,fields,super)
    # model_class(name,fields,super)
    # builder_interface(name,fields,super)
    # builder_class(name,fields,super)
    # repository_interface(name,fields,super)
    # manager_interface(name,fields,super)
    # request(name,fields,super)
    # response(name,fields,super)
    # controller(name)
    # assembler(name,fields,super)
    # mapping(name)
    # factory(name)

    for name in names:
        authority(name)









